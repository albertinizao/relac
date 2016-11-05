package com.opipo.relac.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

		ResponseEntity<Collection<String>> actual = characterController.list();

		assertNotNull("There is no actual",actual);
		assertNotNull("There is no actual",actual.getBody());
		assertTrue("Actual doesn't contains the expected",actual.getBody().containsAll(expected));
		assertTrue("Expected doesn't contains the actual",expected.containsAll(actual.getBody()));
	}
	
	@Test
	public void givenNameThenReturnCharacter(){
		String name = "Character's Name";
		Character expected = new Character();
		
		Mockito.when(characterService.get(name)).thenReturn(expected);
		
		ResponseEntity<Character> actual = characterController.get(name);
		
		assertNotNull("Actual is null",actual);
		assertNotNull("Actual is null",actual.getBody());
		assertEquals("Actual isn't the expected",expected,actual.getBody());
	}
	
	@Test
	public void givenCharacterThenSaveIt(){
		Character character = new Character();
		
		Mockito.verify(characterService).save(character);
		
		ResponseEntity response = characterController.save(character);
		
		assertNotNull("Response is null",response);
		assertNotNull("Response is null",response.getStatusCode());
		assertEquals("HTTPCode isn't created", response.getStatusCode(), HttpStatus.ACCEPTED);
	}
	
	@Test
	public void givenNameThenCreateCharacter(){
		String name = "Character's name";
		Character character = new Character();
		
		ArgumentCaptor<Character> characterCaptor = ArgumentCaptor.forClass(Character.class);
		
		Mockito.verify(characterService).save(characterCaptor.capture());
		ResponseEntity response = characterController.create(name);
		
		assertNotNull("Character isn't send to service",characterCaptor.getValue());
		assertEquals("The name is not the expected", name, characterCaptor.getValue().getName());
		assertNotNull("Response is null",response);
		assertNotNull("Response is null",response.getStatusCode());
		assertEquals("HTTPCode isn't created", response.getStatusCode(), HttpStatus.CREATED);
	}
}
