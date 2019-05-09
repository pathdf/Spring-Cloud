package com.pankaj.spring.cloud.apigateway.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.netflix.infix.lang.infix.antlr.EventFilterParser.null_predicate_return;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.pankaj.spring.cloud.apigateway.client.UserServiceClient;

public class PostFilter extends BaseFilter {

private static Logger log = LoggerFactory.getLogger(PreFilter.class);
    
    @Autowired
    private UserServiceClient userServiceClient;
    
    @Override
    public String filterType() {
        return "post";
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
    	String uri = req.getRequestURI();
			String token = (String)session.getAttribute(AUTH_TOKEN_HEADER);
			if(uri.contains("/api/protected/user/logout") ){
				if(token != null){
					session.removeAttribute(AUTH_TOKEN_HEADER);
					ctx.setResponseBody(LOGGED_OUT);
					ctx.setResponseStatusCode(HttpStatus.OK.value());
					ctx.setSendZuulResponse(false);
					
				}
				
				session.invalidate();
		    }
			return null;
    }
}
