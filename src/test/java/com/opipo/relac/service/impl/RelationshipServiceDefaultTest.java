package com.opipo.relac.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.opipo.relac.exception.NotFoundElement;
import com.opipo.relac.model.Character;
import com.opipo.relac.model.Relationship;
import com.opipo.relac.service.CharacterService;

@RunWith(MockitoJUnitRunner.class)
public class RelationshipServiceDefaultTest {
	@InjectMocks
	private RelationshipServiceDefault relationshipService;

	@Mock
	private CharacterService characterService;

	@Test
	public void givenOwnerNameThenListRelationships() {
		String ownerName = "owner";
		Character character = new Character();
		character.setName(ownerName);
		List<Relationship> relationships = new ArrayList<>();
		character.setRelationships(relationships);
		Mockito.when(characterService.get(ownerName)).thenReturn(character);
		List<Relationship> actual = relationshipService.list(ownerName);
		assertNotNull(actual);
		assertTrue(actual.containsAll(relationships));
		assertTrue(relationships.containsAll(actual));
	}

	@Test(expected = NotFoundElement.class)
	public void givenOwnerNameAndOtherNameThenGetTheRelationship() {
		String ownerName = "owner";
		String otherName = "otherName";
		String thirthName = "NAME";
		Character owner = new Character();
		owner.setName(ownerName);
		Relationship ownerRelationship = new Relationship();
		ownerRelationship.setCharacterName(otherName);
		Relationship ownerRelationship2 = new Relationship();
		ownerRelationship.setCharacterName(thirthName);
		List<Relationship> ownerRelationships = new ArrayList<>();
		ownerRelationships.add(ownerRelationship);
		ownerRelationships.add(ownerRelationship2);
		owner.setRelationships(ownerRelationships);

		Mockito.when(characterService.get(ownerName)).thenReturn(owner);

		Relationship actual = relationshipService.get(ownerName, otherName);

		assertNotNull(actual);
		assertEquals(otherName, actual.getCharacterName());
	}

	@Test(expected = NotFoundElement.class)
	public void givenOwnerNameAndOtherNameNotFoundThenGetTheRelationship() {
		String ownerName = "owner";
		String otherName = "otherName";
		String thirthName = "NAME";
		Character owner = new Character();
		owner.setName(ownerName);
		Relationship ownerRelationship = new Relationship();
		ownerRelationship.setCharacterName(otherName);
		Relationship ownerRelationship2 = new Relationship();
		ownerRelationship.setCharacterName(thirthName);
		List<Relationship> ownerRelationships = new ArrayList<>();
		ownerRelationships.add(ownerRelationship);
		ownerRelationships.add(ownerRelationship2);
		owner.setRelationships(ownerRelationships);

		Mockito.when(characterService.get(ownerName)).thenReturn(owner);

		Relationship actual = relationshipService.get(ownerName, "missName");

		assertNull(actual);
	}

	@Test
	public void givenOwnerNameAndNewOtherNameAndRelationshipThenSaveIt() {
		String ownerName = "owner";
		String otherName = "otherName";
		Character owner = new Character();
		owner.setName(ownerName);
		Relationship ownerRelationship = new Relationship();
		ownerRelationship.setCharacterName(otherName);
		List<Relationship> ownerRelationships = new ArrayList<>();
		ownerRelationships.add(ownerRelationship);

		Mockito.when(characterService.get(ownerName)).thenReturn(owner);

		Relationship relationshipToSave = new Relationship();
		relationshipToSave.setCharacterName(otherName);

		relationshipService.save(ownerName, relationshipToSave);

		ArgumentCaptor<Character> characterCaptor = ArgumentCaptor.forClass(Character.class);
		Mockito.verify(characterService).save(characterCaptor.capture());
		Character characterCaptured = characterCaptor.getValue();
		assertNotNull(characterCaptured);
		assertEquals(ownerName, characterCaptured.getName());
		assertNotNull(characterCaptured.getRelationships());
		assertTrue(characterCaptured.getRelationships().stream()
				.anyMatch(p -> p.getCharacterName().equalsIgnoreCase(otherName)));
		assertTrue(characterCaptured.getRelationships().stream().anyMatch(p -> p.equals(relationshipToSave)));
	}

	@Test
	public void givenOwnerNameAndOtherNameAndRelationshipThenSaveIt() {
		String ownerName = "owner";
		String otherName = "otherName";
		Character owner = new Character();
		owner.setName(ownerName);
		Relationship ownerRelationship = new Relationship();
		ownerRelationship.setCharacterName(otherName);
		List<Relationship> ownerRelationships = new ArrayList<>();
		ownerRelationships.add(ownerRelationship);
		owner.setRelationships(ownerRelationships);

		Mockito.when(characterService.get(ownerName)).thenReturn(owner);

		Relationship relationshipToSave = new Relationship();
		relationshipToSave.setCharacterName(otherName);

		relationshipService.save(ownerName, relationshipToSave);

		ArgumentCaptor<Character> characterCaptor = ArgumentCaptor.forClass(Character.class);
		Mockito.verify(characterService).save(characterCaptor.capture());
		Character characterCaptured = characterCaptor.getValue();
		assertNotNull(characterCaptured);
		assertEquals(ownerName, characterCaptured.getName());
		assertNotNull(characterCaptured.getRelationships());
		assertTrue(characterCaptured.getRelationships().stream()
				.anyMatch(p -> p.getCharacterName().equalsIgnoreCase(otherName)));
		assertTrue(characterCaptured.getRelationships().stream().anyMatch(p -> p.equals(relationshipToSave)));
	}

	@Test
	public void givenOwnerNameAndOtherNameThenDeleteIt() {
		String ownerName = "owner";
		String otherName = "otherName";
		Character owner = new Character();
		owner.setName(ownerName);
		Relationship ownerRelationship = new Relationship();
		ownerRelationship.setCharacterName(otherName);
		List<Relationship> ownerRelationships = new ArrayList<>();
		ownerRelationships.add(ownerRelationship);
		owner.setRelationships(ownerRelationships);

		Mockito.when(characterService.get(ownerName)).thenReturn(owner);

		Relationship relationshipToSave = new Relationship();
		relationshipToSave.setCharacterName(otherName);

		relationshipService.delete(ownerName, otherName);

		ArgumentCaptor<Character> characterCaptor = ArgumentCaptor.forClass(Character.class);
		Mockito.verify(characterService).save(characterCaptor.capture());
		Character characterCaptured = characterCaptor.getValue();
		assertNotNull(characterCaptured);
		assertEquals(ownerName, characterCaptured.getName());
		assertNotNull(characterCaptured.getRelationships());
		assertFalse(characterCaptured.getRelationships().stream()
				.anyMatch(p -> p.getCharacterName().equalsIgnoreCase(otherName)));
		assertFalse(characterCaptured.getRelationships().stream().anyMatch(p -> p.equals(relationshipToSave)));
	}

}
