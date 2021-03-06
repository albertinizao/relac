package com.opipo.relac.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.opipo.relac.model.Character;
import com.opipo.relac.model.Relationship;
import com.opipo.relac.repository.CharacterRepository;
import com.opipo.relac.service.UserService;;

@RunWith(MockitoJUnitRunner.class)
public class CharacterServiceDefaultTest {
	@InjectMocks
	private CharacterServiceDefault characterService;

	@Mock
	private CharacterRepository characterRepository;
	@Mock
	private UserService userService;

	@Test
	public void givenNothingReturnAllCharacters() {
		Character characters = new Character();
		List<Character> expected = new ArrayList<>();
		expected.add(characters);
		Mockito.when(characterRepository.findAll()).thenReturn(expected);
		List<Character> actual = characterService.list(null);
		assertEquals(expected, actual);

	}

	@Test
	public void givenNameThenReturnCharacter() {
		String name = "character's name";
		Character expected = new Character();
		Mockito.when(characterRepository.findOne(name)).thenReturn(expected);
		Character actual = characterService.get(name);
		assertEquals(expected, actual);
	}

	@Test
	public void givenCharacterUncompleteThenSaveIt(){
		String name = "character's name";
		String user = "user";
		Character givenCharacter = new Character();
		givenCharacter.setName(name);
		Character completeCharacter = new Character();
		completeCharacter.setName(name+"incorrect");
		completeCharacter.setUser(user);
		List<Relationship> relationships = new ArrayList<>();
		completeCharacter.setRelationships(relationships);
		Mockito.when(characterRepository.findOne(name)).thenReturn(completeCharacter);
		Mockito.when(userService.userIsAdmin()).thenReturn(false);
		Mockito.when(userService.getUserIdentifier()).thenReturn(user);
		characterService.save(givenCharacter);
		ArgumentCaptor<Character> characterCaptor = ArgumentCaptor.forClass(Character.class);
		Mockito.verify(characterRepository).save(characterCaptor.capture());
		Character characterCaptured = characterCaptor.getValue();
		assertNotNull(characterCaptured);
		assertEquals(name,characterCaptured.getName());
		assertEquals(relationships,characterCaptured.getRelationships());
	}

	@Test
	public void givenCharacterUncompleteThenSaveIt2(){
		String name = "character's name";
		String user = "user";
		Character givenCharacter = new Character();
		givenCharacter.setName(name);
		givenCharacter.setUser(user);
		Mockito.when(characterRepository.findOne(name)).thenReturn(null);
		Mockito.when(userService.userIsAdmin()).thenReturn(true);
		Mockito.when(userService.getUserIdentifier()).thenReturn(user);
		characterService.save(givenCharacter);
		ArgumentCaptor<Character> characterCaptor = ArgumentCaptor.forClass(Character.class);
		Mockito.verify(characterRepository).save(characterCaptor.capture());
		Character characterCaptured = characterCaptor.getValue();
		assertNotNull(characterCaptured);
		assertEquals(name,characterCaptured.getName());
		assertTrue(characterCaptured.getRelationships().isEmpty());
	}
	
	@Test
	public void givenCharacterNameThenDeleteIT(){
		String name = "character's name";
		String user = "user";
		Character character = new Character();
		character.setName(name);
		character.setUser(user);
		Mockito.when(characterRepository.findOne(name)).thenReturn(character);
		Mockito.when(userService.userIsAdmin()).thenReturn(true);
		Mockito.when(userService.getUserIdentifier()).thenReturn(user);
		characterService.delete(name);
		Mockito.verify(characterRepository).delete(name);
		
	}
}
