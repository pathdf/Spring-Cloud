package com.pankaj.spring.cloud.apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class AuthFilter extends RequestHeaderAuthenticationFilter {

	public AuthFilter() {
		this.setExceptionIfHeaderMissing(false);
		this.setPrincipalRequestHeader("X-Auth-Token");
	}

	@Override
	@Autowired
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
	}
}