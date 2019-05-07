package com.pankaj.spring.cloud.common.auth;

import java.util.Base64;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TokenUtil {

	public static final String USER_NAME = "userName";
	public static final String PASSWORD = "password";
	public static final String ROLES = "roles";
	
	public static String createToken(TokenDTO dto) {
		JSONObject obj = new JSONObject();
		obj.put(USER_NAME, dto.userName);
		obj.put(PASSWORD, dto.password);
		JSONArray roles = new JSONArray();

		for (String role : dto.roles) {
			roles.add(role);
		}

		obj.put(ROLES, roles);
		String token = obj.toString();
		return Base64.getEncoder().encodeToString(token.getBytes());
	}
	
	public static TokenDTO parseToken(String token) {
		try {
			String decodedToken = new String(Base64.getDecoder().decode(token));

			JSONObject obj = (JSONObject) org.json.simple.JSONValue.parse(decodedToken);

			ObjectMapper mapper = new ObjectMapper();

			return mapper.convertValue(obj, TokenDTO.class);
		} catch (Exception ex) {
			return null;
		}
	}
}
