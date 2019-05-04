package com.pankaj.spring.cloud.apigateway.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pankaj.spring.cloud.apigateway.domain.User;
import com.pankaj.spring.cloud.apigateway.repository.UserRepository;
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String s)
			throws UsernameNotFoundException {
		User user = userRepository.findByUserName(s);

		if (user == null) {
			throw new UsernameNotFoundException(String.format(
					"The username %s doesn't exist", s));
		}

		List<GrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		});

		UserDetails userDetails = new org.springframework.security.core.userdetails.User(
				user.getUserName(), user.getPassword(), authorities);

		return userDetails;
	}

}