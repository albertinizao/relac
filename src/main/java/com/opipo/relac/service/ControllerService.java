package com.opipo.relac.service;

import java.util.Collection;

public interface ControllerService {
	Collection<Character> list();
	Character get(String name);
	Character save(Character character);
}
