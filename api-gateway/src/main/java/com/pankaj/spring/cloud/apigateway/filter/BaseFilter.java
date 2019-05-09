package com.pankaj.spring.cloud.apigateway.filter;

import com.netflix.zuul.ZuulFilter;

public abstract class BaseFilter extends ZuulFilter {
	public static final String AUTH_TOKEN_HEADER = "X-Auth-Token";
	public static final String UNAUTHORIZED = "Unauthorized";
	public static final String INTERNAL = "Internal use only";
	public static final String LOGGED_OUT = "Logged out";
}
