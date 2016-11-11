package com.opipo.relac.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.opipo.relac.model.Relation;
import com.opipo.relac.service.RelationService;

@RunWith(MockitoJUnitRunner.class)
public class RelationControllerTest {
	@Mock
	private RelationService relationService;

	@InjectMocks
	private RelationController relationController;

	@Test
	public void givenOwnerIdAndOtherCharIdThenReturnRelationsDates() {
		String ownerName = "Owner Name";
		String otherName = "Other name";
		Date date = new Date();
		Relation relation = new Relation();
		relation.setDate(date);
		List<Relation> relations = new ArrayList<>();
		relations.add(relation);

		Mockito.when(relationService.list(ownerName, otherName)).thenReturn(relations);

		ResponseEntity<List<Date>> response = relationController.list(ownerName, otherName);

		assertNotNull(response);
		assertNotNull(response.getBody());
		List<Date> dates = response.getBody();
		assertNotNull(dates);
		assertFalse(dates.isEmpty());
		assertTrue(dates.contains(date));
		assertEquals(relations.size(), dates.size());
		assertEquals("HTTPCode isn't ok", response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void givenOwnerIdAndOtherCharIdAndDateThenReturnIt() {
		String ownerName = "Owner Name";
		String otherName = "Other name";
		Date date = new Date();
		Relation relation = new Relation();
		relation.setDate(date);

		Mockito.when(relationService.get(ownerName, otherName, date)).thenReturn(relation);

		ResponseEntity<Relation> response = relationController.get(ownerName, otherName, date.getTime());

		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(date, response.getBody().getDate());
		assertEquals("HTTPCode isn't ok", response.getStatusCode(), HttpStatus.OK);

	}

	@Test
	public void givenOwnerIdAndOthercharIdAndDateThenCreateIt() {
		String ownerName = "Owner Name";
		String otherName = "Other name";
		Date date = new Date();
		Relation relation = new Relation();
		relation.setDate(date);

		ArgumentCaptor<Relation> relationCaptor = ArgumentCaptor.forClass(Relation.class);
		ResponseEntity response = relationController.create(ownerName, otherName, date.getTime());

		Mockito.verify(relationService).save(Mockito.eq(ownerName), Mockito.eq(otherName), relationCaptor.capture());

		assertNotNull("Character isn't send to service", relationCaptor.getValue());
		assertEquals("The date is not the expected", date, relationCaptor.getValue().getDate());
		assertNotNull("Response is null", response);
		assertNotNull("Response is null", response.getStatusCode());
		assertEquals("HTTPCode isn't created", response.getStatusCode(), HttpStatus.CREATED);

	}

	@Test
	public void givenOwnerIdAndOthercharIdAndRelationThenSaveIt() {
		String ownerName = "Owner Name";
		String otherName = "Other name";
		Date date = new Date();
		Relation relation = new Relation();
		relation.setDate(date);

		Mockito.when(relationService.save(ownerName, otherName, relation)).thenReturn(relation);
		ResponseEntity response = relationController.save(ownerName, otherName, date.getTime(), relation);

		assertNotNull("Response is null", response);
		assertNotNull("Response is null", response.getStatusCode());
		assertEquals("HTTPCode isn't created", response.getStatusCode(), HttpStatus.ACCEPTED);

	}

}
