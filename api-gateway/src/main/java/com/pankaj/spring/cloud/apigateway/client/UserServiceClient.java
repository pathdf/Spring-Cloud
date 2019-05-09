package com.pankaj.spring.cloud.apigateway.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pankaj.spring.cloud.apigateway.model.LoginRequest;

@FeignClient(name="user-service")
public interface UserServiceClient {

	@RequestMapping(method=RequestMethod.POST, value="/api/public/user/authenticate")
	ResponseEntity<String> authenticate(@RequestBody LoginRequest loginRequest);
	
	@RequestMapping(method=RequestMethod.GET, value="/api/protected/user/logout")
	ResponseEntity<String> logout();
}
