package com.pankaj.spring.cloud.apigateway.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserInfo extends User {

	private String userName;
	private String password;
	
	public UserInfo(TokenDTO dto, Collection<? extends GrantedAuthority> authorities){
		super(dto.userName, dto.password, authorities);
		this.userName = dto.userName;
		this.password = dto.password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
