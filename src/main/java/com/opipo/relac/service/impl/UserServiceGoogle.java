package com.opipo.relac.service.impl;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opipo.relac.service.UserService;

@Service
public class UserServiceGoogle implements UserService {

	@Override
	public String getUserIdentifier() {
		Authentication authGeneric =SecurityContextHolder.getContext().getAuthentication();
		if (authGeneric instanceof AnonymousAuthenticationToken){
			AnonymousAuthenticationToken auth = (AnonymousAuthenticationToken) authGeneric;
			return auth.getName();
		}else{
			OAuth2Authentication auth = (OAuth2Authentication) authGeneric;
			ObjectMapper mapper = new ObjectMapper();
			try {
				return (String) ((HashMap) ((HashMap) mapper.readValue(mapper.writeValueAsString(auth), HashMap.class)
						.get("userAuthentication")).get("details")).get("email");
			} catch (IOException e) {
				throw new InvalidRequestException("User is invalid");
			}
		}
	}

}
