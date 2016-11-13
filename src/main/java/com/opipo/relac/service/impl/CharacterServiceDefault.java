package com.opipo.relac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opipo.relac.exception.InvalidUser;
import com.opipo.relac.exception.NotFoundElement;
import com.opipo.relac.model.Character;
import com.opipo.relac.repository.CharacterRepository;
import com.opipo.relac.service.CharacterService;
import com.opipo.relac.service.UserService;

@Service
public class CharacterServiceDefault implements CharacterService {

	@Autowired
	private CharacterRepository characterRepository;

	@Autowired
	private UserService userService;

	@Override
	public List<Character> list() {
		return characterRepository.findAll();
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
	
	private void validateUser(Character character){
		String user = userService.getUserIdentifier();
		if (!character.getUser().equalsIgnoreCase(user) && !userService.userIsAdmin()) {
			throw new InvalidUser(user);
		}
	}

	
	private void validateUser(String name){
		validateUser(characterRepository.findOne(name));
	}

}
