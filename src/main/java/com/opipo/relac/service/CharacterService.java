package com.opipo.relac.service;

import java.util.List;

import com.opipo.relac.model.Character;

public interface CharacterService {
	List<Character> list();
	Character get(String name);
	Character save(Character character);
	Character saveOverride(Character character);
	void delete(String name);
}
