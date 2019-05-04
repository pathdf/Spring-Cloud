package com.pankaj.spring.cloud.apigateway.component;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import com.pankaj.spring.cloud.apigateway.dto.UserInfo;

public class AuthToken extends AbstractAuthenticationToken {
	public static final String HEADER = "X-Auth-Token";

	private UserInfo principal;
	private String token;

	public AuthToken(UserInfo principal, String token) {
		super(principal.getAuthorities());

		this.principal = principal;
		this.token = token;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}

	public String getToken() {
		return this.token;
	}
}