package com.opipo.relac.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.opipo.relac.model.CreationUser;
import com.opipo.relac.model.User;
import com.opipo.relac.model.UserAuthentication;
import com.opipo.relac.model.UserRole;
import com.opipo.relac.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

	@RequestMapping(value = "", method = RequestMethod.DELETE)
	public void logout() {
		OAuth2Authentication authentication = (OAuth2Authentication) SecurityContextHolder.getContext()
				.getAuthentication();
		authentication.eraseCredentials();
	}

	@Autowired
	UserRepository userRepository;

	@RequestMapping(value = "/current", method = RequestMethod.GET)
	public User getCurrent() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof UserAuthentication) {
			return ((UserAuthentication) authentication).getDetails();
		}
		return new User(authentication.getName()); // anonymous user support
	}

	@RequestMapping(value = "/current", method = RequestMethod.PATCH)
	public ResponseEntity<String> changePassword(@RequestBody final User user) {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final User currentUser = userRepository.findByUsername(authentication.getName());

		if (user.getNewPassword() == null || user.getNewPassword().length() < 4) {
			return new ResponseEntity<String>("new password to short", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		final BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
		if (!pwEncoder.matches(user.getPassword(), currentUser.getPassword())) {
			return new ResponseEntity<String>("old password mismatch", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		currentUser.setPassword(pwEncoder.encode(user.getNewPassword()));
		userRepository.save(Arrays.asList(new User[] { currentUser }));
		return new ResponseEntity<String>("password changed", HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity create(@RequestBody final CreationUser userGiven) {
		if (userRepository.findByUsername(userGiven.getUsername()) == null) {
			User user = new User();
			user.setUsername(userGiven.getUsername());
			user.setPassword(new BCryptPasswordEncoder().encode(userGiven.getPassword()));
			user.grantRole(UserRole.USER);
			userRepository.save(user);
			return new ResponseEntity(HttpStatus.CREATED);
		} else {
			return new ResponseEntity(HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<User> list() {
		return userRepository.findAll();
	}
}
