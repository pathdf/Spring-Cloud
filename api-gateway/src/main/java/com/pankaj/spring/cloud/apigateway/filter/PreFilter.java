package com.pankaj.spring.cloud.apigateway.filter;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.pankaj.spring.cloud.apigateway.client.UserServiceClient;
import com.pankaj.spring.cloud.apigateway.model.LoginRequest;

public class PreFilter extends BaseFilter {
	
	private static Logger log = LoggerFactory.getLogger(PreFilter.class);
    
    @Autowired
    private UserServiceClient userServiceClient;
    
    @Override
    public String filterType() {
        return "pre";
    }
    @Override
    public int filterOrder() {
        return 1;
    }
    @Override
    public boolean shouldFilter() {
        return true;
    }
    @Override
    public Object run() {
    	RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest req = ctx.getRequest();
		HttpSession session = req.getSession();
		try {
			String authToken = null;
			String uri = req.getRequestURI();
			if(uri.contains("/v2/api-docs")){
				return null;
			}else if (uri.contains("/api/public/user/authenticate") && req.getMethod().equals("POST")){
				session.removeAttribute(AUTH_TOKEN_HEADER);
				String data = req.getReader().lines().collect(Collectors.joining());
				JSONParser parser = new JSONParser();
				JSONObject json = (JSONObject) parser.parse(data);
				ObjectMapper mapper = new ObjectMapper();
				LoginRequest loginRequest = mapper.convertValue(json, LoginRequest.class);

				ResponseEntity<String> tokenMsg = userServiceClient.authenticate(loginRequest);

				authToken = tokenMsg.getBody();

				if (authToken != null) {
					session.setAttribute(AUTH_TOKEN_HEADER, authToken);
					ctx.setResponseStatusCode(HttpStatus.OK.value());
					ctx.setSendZuulResponse(false);

					return null;
				}
			}
			authToken = (String)session.getAttribute(AUTH_TOKEN_HEADER);
			ctx.addZuulRequestHeader(AUTH_TOKEN_HEADER, authToken);
		} catch (Exception e) {
			// TODO: handle exception
		}
	/*	if(!((String)session.getAttribute(AUTH_TOKEN_HEADER)).isEmpty()){
			req.setAttribute(AUTH_TOKEN_HEADER,session.getAttribute(AUTH_TOKEN_HEADER));
		}*/
        return null;
    }
}