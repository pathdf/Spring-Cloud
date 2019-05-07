package com.pankaj.spring.cloud.common.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseAuthController /*extends BaseController*/ {

    protected AuthToken getAuthToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getClass().isAssignableFrom(AuthToken.class))
            return (AuthToken)auth;

        return null;
    }

    protected UserInfo getUserInfo() {
        AuthToken auth = this.getAuthToken();

        if (auth != null)
            return (UserInfo)auth.getPrincipal();

        return null;
    }
}
