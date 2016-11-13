package com.opipo.relac.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.opipo.relac.model.Relationship;
import com.opipo.relac.service.RelationshipService;

@RunWith(MockitoJUnitRunner.class)
public class RelationshipControllerTest {
	@Mock
	private RelationshipService relationshipService;

	@InjectMocks
	private RelationshipController relationshipController;

	@Test
	public void givenOwnerNameThenList() {
		String ownersName = "owner Name";
		List<Relationship> relationships = new ArrayList<>();
		Relationship relationship = new Relationship();
		relationships.add(relationship);
		Mockito.when(relationshipService.list(ownersName)).thenReturn(relationships);
		ResponseEntity<Collection<String>> response = relationshipController.list(ownersName);
		assertNotNull("There is no response", response);
		assertNotNull("There is no response body", response.getBody());
		assertTrue("There is no the expected response", relationships.stream().map(f -> f.getCharacterName())
				.collect(Collectors.toList()).containsAll(response.getBody()));
		assertTrue("There is no the expected response", response.getBody()
				.containsAll(relationships.stream().map(f -> f.getCharacterName()).collect(Collectors.toList())));
		assertEquals("HTTPCode isn't correct", response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void givenOwnerNameAndOtherNameThenGetIt() {
		String ownersName = "owner Name";
		String otherName = "character name";
		Relationship relationship = new Relationship();
		relationship.setCharacterName(otherName);
		Mockito.when(relationshipService.get(ownersName, otherName)).thenReturn(relationship);
		ResponseEntity<Relationship> response = relationshipController.get(ownersName, otherName);
		assertNotNull("There is no response", response);
		assertNotNull("There is no response body", response.getBody());
		assertEquals("There is no the expected response", relationship, response.getBody());
		assertEquals("HTTPCode isn't correct", response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void givenOwnerNameAndRelationshipThenSaveIt() {
		String ownersName = "owner Name";
		String otherName = "character name";
		Relationship relationship = new Relationship();
		relationship.setCharacterName(otherName);
		ResponseEntity response = relationshipController.save(ownersName, otherName, relationship);
		Mockito.verify(relationshipService).save(ownersName, relationship);
		assertNotNull("There is no response", response);
		assertEquals("HTTPCode isn't correct", response.getStatusCode(), HttpStatus.ACCEPTED);
	}

	@Test(expected = IllegalArgumentException.class)
	public void givenOwnerNameAndRelationshipBrokenThenSaveIt() {
		String ownersName = "owner Name";
		String otherName = "character name";
		Relationship relationship = new Relationship();
		relationship.setCharacterName(otherName);
		ResponseEntity response = relationshipController.save(ownersName, ownersName, relationship);
	}

	@Test
	public void givenOwnerNameAndRelationshipThenCreateIt() {
		String ownersName = "owner Name";
		String otherName = "character name";

		ArgumentCaptor<Relationship> relationshipCaptor = ArgumentCaptor.forClass(Relationship.class);
		ResponseEntity response = relationshipController.create(ownersName, otherName);
		Mockito.verify(relationshipService).save(Mockito.eq(ownersName), relationshipCaptor.capture());
		assertNotNull("There is no response", response);
		assertEquals("The name is not the expected", otherName, relationshipCaptor.getValue().getCharacterName());
		assertNotNull("Response is null", response);
		assertNotNull("Response is null", response.getStatusCode());
		assertEquals("HTTPCode isn't created", response.getStatusCode(), HttpStatus.CREATED);
	}

}
