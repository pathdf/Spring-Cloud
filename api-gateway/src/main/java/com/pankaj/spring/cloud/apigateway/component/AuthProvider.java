package com.pankaj.spring.cloud.apigateway.component;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import com.pankaj.spring.cloud.apigateway.dto.TokenDTO;
import com.pankaj.spring.cloud.apigateway.dto.UserInfo;
import com.pankaj.spring.cloud.apigateway.util.TokenUtil;

@Component
public class AuthProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		if (auth.getClass().isAssignableFrom(PreAuthenticatedAuthenticationToken.class) && auth.getPrincipal() != null) {
			String token = (String)auth.getPrincipal();

			UserInfo user = parseToken(token);

			if (user == null)
				throw new BadCredentialsException("Invalid	 token.");

			return new AuthToken(user, token);
		}

		return auth;
	}

	private UserInfo parseToken(String token) {
		TokenDTO dto = TokenUtil.parseToken(token);

		if (dto == null)
			return null;

		List<GrantedAuthority> authorities = dto.roles
				.stream()
				.map(role -> new SimpleGrantedAuthority(role))
				.collect(Collectors.toList());

		return new UserInfo(dto, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(PreAuthenticatedAuthenticationToken.class) || authentication.isAssignableFrom(AuthToken.class);
	}
}