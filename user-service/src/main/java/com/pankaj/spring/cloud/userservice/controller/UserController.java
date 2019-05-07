package com.pankaj.spring.cloud.userservice.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pankaj.spring.cloud.common.auth.BaseAuthController;
import com.pankaj.spring.cloud.common.auth.LoginUser;
import com.pankaj.spring.cloud.common.auth.TokenDTO;
import com.pankaj.spring.cloud.common.auth.TokenUtil;
import com.pankaj.spring.cloud.userservice.domain.Role;
import com.pankaj.spring.cloud.userservice.domain.User;
import com.pankaj.spring.cloud.userservice.repository.UserRepository;

@RestController
@RequestMapping("/api/public/user")
public class UserController extends BaseAuthController{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@ApiOperation(value = "To authenticate the user", response = TokenDTO.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully authenticate"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	//@RequestMapping(value="/authenticate", method=RequestMethod.POST)
	@PostMapping("/authenticate")
	public ResponseEntity<String> authenticate(@RequestBody LoginUser loginUser){
		String token = null;
		if(loginUser != null){
			User user = userRepository.findByUserName(loginUser.getUserName());
			if( user != null &&  passwordEncoder.matches(loginUser.getPassword(), user.getPassword())){
				TokenDTO dto = new TokenDTO();
				dto.userName = loginUser.getUserName();
				dto.password = loginUser.getPassword();
				List<String> roles = new ArrayList<String>();
				for(Role role : user.getRoles()){
					roles.add(role.getRoleName());
				}
				dto.roles = roles;
				token = TokenUtil.createToken(dto);
				return new ResponseEntity(token, HttpStatus.OK);
			}
			return new ResponseEntity("User Not Found", HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		}
		return new ResponseEntity("Enter your Credential", HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "To sign up the user", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully saved user"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	//@RequestMapping(value="/signup", method=RequestMethod.POST)
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody LoginUser loginUser){
		if(loginUser != null){
			User user = new User();
			user.setUserName(loginUser.getUserName());
			user.setPassword(passwordEncoder.encode(loginUser.getPassword()));
			user.setLastName("lastname");
			user.setFirstName("firstName");
			Role role = new Role();
			role.setId(1L);
			List<Role> roles = new ArrayList<Role>();
			roles.add(role);
			user.setRoles(roles);
			userRepository.save(user);
		}
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@ApiOperation(value = "To Test whether the user is authorized to access this link or not", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User is authorized"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	//@RequestMapping(value="/signup", method=RequestMethod.POST)
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	@PostMapping("/testAuthorization")
	public ResponseEntity<?> signup2(@RequestBody LoginUser loginUser){
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
}
