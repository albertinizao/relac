package com.opipo.relac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opipo.relac.model.Character;
import com.opipo.relac.repository.CharacterRepository;
import com.opipo.relac.service.CharacterService;

@Service
public class CharacterServiceDefault implements CharacterService {

	@Autowired
	private CharacterRepository characterRepository;
	
	@Override
	public List<Character> list() {
		return characterRepository.findAll();
	}

	@Override
	public Character get(String name) {
		return characterRepository.findOne(name);
	}

	@Override
	public Character save(Character character) {
		Character completeCharacter = this.get(character.getName());
		character.setRelationships(completeCharacter.getRelationships());
		return characterRepository.save(character);
	}

	@Override
	public void delete(String name) {
		characterRepository.delete(name);
	}

}
