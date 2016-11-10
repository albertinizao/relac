package com.opipo.relac.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import com.opipo.relac.repository.CharacterRepository;;

@RunWith(MockitoJUnitRunner.class)
public class CharacterServiceDefaultTest {
	@InjectMocks
	private CharacterServiceDefault characterService;

	@Mock
	private CharacterRepository characterRepository;

	@Test
	public void givenNothingReturnAllCharacters() {
		Character characters = new Character();
		List<Character> expected = new ArrayList<>();
		expected.add(characters);
		Mockito.when(characterRepository.findAll()).thenReturn(expected);
		List<Character> actual = characterService.list();
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
		Character givenCharacter = new Character();
		givenCharacter.setName(name);
		Character completeCharacter = new Character();
		completeCharacter.setName(name+"incorrect");
		List<Relationship> relationships = new ArrayList<>();
		completeCharacter.setRelationships(relationships);
		Mockito.when(characterRepository.findOne(name)).thenReturn(completeCharacter);
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
		Character givenCharacter = new Character();
		givenCharacter.setName(name);
		Mockito.when(characterRepository.findOne(name)).thenReturn(null);
		characterService.save(givenCharacter);
		ArgumentCaptor<Character> characterCaptor = ArgumentCaptor.forClass(Character.class);
		Mockito.verify(characterRepository).save(characterCaptor.capture());
		Character characterCaptured = characterCaptor.getValue();
		assertNotNull(characterCaptured);
		assertEquals(name,characterCaptured.getName());
		assertEquals(null,characterCaptured.getRelationships());
	}
	
	@Test
	public void givenCharacterNameThenDeleteIT(){
		String name = "character's name";
		characterService.delete(name);
		Mockito.verify(characterRepository).delete(name);
		
	}
}
