package com.opipo.relac.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opipo.relac.exception.InvalidUser;
import com.opipo.relac.exception.NotFoundElement;
import com.opipo.relac.model.Character;
import com.opipo.relac.model.UserAuthentication;
import com.opipo.relac.model.UserRole;
import com.opipo.relac.repository.CharacterRepository;
import com.opipo.relac.service.CharacterService;
import com.opipo.relac.service.UserService;

@Service
public class CharacterServiceDefault implements CharacterService {

	@Autowired
	private CharacterRepository characterRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private HttpServletRequest httpServletRequest;

	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;

	@Override
	public List<Character> list(String game) {
		return game == null ? characterRepository.findAll() : characterRepository.findByGame(game);
	}

	@Override
	public Character get(String name) {
		Character character = characterRepository.findOne(name);
		if (character == null) {
			throw new NotFoundElement("character");
		}
		return character;
	}

	@Override
	public Character save(Character character) {
		Character completeCharacter = characterRepository.findOne(character.getName());
		character.setUser(completeCharacter == null ? userService.getUserIdentifier() : completeCharacter.getUser());
		validateUser(character);
		character.setRelationships(completeCharacter == null ? null : completeCharacter.getRelationships());
		return saveOverride(character);
	}

	@Override
	public Character saveOverride(Character character) {
		validateUser(character);
		return characterRepository.save(character);
	}

	@Override
	public void delete(String name) {
		validateUser(name);
		characterRepository.delete(name);
	}

	private void validateUser(Character character) {
		UserAuthentication auth = (UserAuthentication) tokenAuthenticationService.getAuthentication(httpServletRequest);
		if (null == auth) {
			throw new InvalidUser("No user");
		} else if (!character.getUser().equalsIgnoreCase(auth.getName())
				&& !auth.getDetails().hasRole(UserRole.ADMIN)) {
			throw new InvalidUser(auth.getName() + " is invalid. The correct is " + character.getName());
		}
	}

	private void validateUser(String name) {
		validateUser(characterRepository.findOne(name));
	}

}
