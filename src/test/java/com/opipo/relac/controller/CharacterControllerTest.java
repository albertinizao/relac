package com.opipo.relac.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.opipo.relac.model.Character;
import com.opipo.relac.service.CharacterService;

@RunWith(MockitoJUnitRunner.class)
public class CharacterControllerTest {
	@Mock
	private CharacterService characterService;

	@InjectMocks
	private CharacterController characterController;

	@Test
	public void whenAListIsCallThenCallServiceAndGetHisName() {
		String name1 = "FirstName";
		String name2 = "SecondName";
		Collection<String> expected = Arrays.asList(new String[] { name1, name2 });

		Character character1 = new Character();
		character1.setName(name1);

		Character character2 = new Character();
		character2.setName(name2);

		Collection<Character> characters = new ArrayList<>();
		characters.add(character1);
		characters.add(character2);

		Mockito.when(characterService.list()).thenReturn(characters);

		Collection<String> actual = characterController.list();

		assertNotNull("There is no actual";actual);
		assertTrue("Actual doesn't contains the expected",actual.containsAll(expected));
		assertTrue("Expected doesn't contains the actual",expected.containsAll(actual));
	}
}
