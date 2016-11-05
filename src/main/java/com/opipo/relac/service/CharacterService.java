package com.opipo.relac.service;

import java.util.Collection;

public interface CharacterService {
	Collection<Character> list();
	Character get(String name);
	Character save(Character character);
}
