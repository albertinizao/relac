package com.opipo.relac.service.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.opipo.relac.exception.NotFoundElement;
import com.opipo.relac.model.Relation;
import com.opipo.relac.model.Relationship;
import com.opipo.relac.service.RelationshipService;

@RunWith(MockitoJUnitRunner.class)
public class RelationServiceDefaultTest {

	@InjectMocks
	private RelationServiceDefault relationService;

	@Mock
	private RelationshipService relationshipService;

	@Test
	public void givenOwnerIdAndOtherIdThenReturnRelations() {
		String ownerName = "owner";
		String otherName = "otherName";
		Date date = new Date();
		Relationship relationship = new Relationship();
		relationship.setCharacterName(otherName);
		List<Relation> relations = new ArrayList<>();
		Relation relation = new Relation();
		relation.setDate(date);
		relations.add(relation);
		relationship.setRelation(relations);

		Mockito.when(relationshipService.get(ownerName, otherName)).thenReturn(relationship);

		List<Relation> relationsActual = relationService.list(ownerName, otherName);

		assertNotNull(relationsActual);
		assertEquals(relations.size(), relationsActual.size());
		assertTrue(relations.containsAll(relationsActual));
		assertTrue(relationsActual.containsAll(relations));
	}

	@Test
	public void givenOwnerIdAndOtherIdAndDateThenGetIt() {
		Date date = new Date();
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(GregorianCalendar.DAY_OF_MONTH, 12);
		String ownerName = "owner";
		String otherName = "otherName";
		Relationship relationship = new Relationship();
		relationship.setCharacterName(otherName);
		List<Relation> relations = new ArrayList<>();
		Relation relation = new Relation();
		relation.setDate(date);
		relations.add(relation);
		Relation relation2 = new Relation();
		relation2.setDate(gc.getTime());
		relations.add(relation2);
		relationship.setRelation(relations);

		Mockito.when(relationshipService.get(ownerName, otherName)).thenReturn(relationship);

		Relation relationActual = relationService.get(ownerName, otherName, date);

		assertNotNull(relationActual);
		assertEquals(date, relationActual.getDate());
	}

	@Test(expected = NotFoundElement.class)
	public void givenOwnerIdAndOtherIdAndMissDateThenGetIt() {
		Date date = new Date();
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(GregorianCalendar.DAY_OF_MONTH, 12);
		String ownerName = "owner";
		String otherName = "otherName";
		Relationship relationship = new Relationship();
		relationship.setCharacterName(otherName);
		List<Relation> relations = new ArrayList<>();
		Relation relation = new Relation();
		relation.setDate(date);
		Relation relation2 = new Relation();
		relation2.setDate(gc.getTime());
		relations.add(relation2);
		relationship.setRelation(relations);

		Mockito.when(relationshipService.get(ownerName, otherName)).thenReturn(relationship);

		relationService.get(ownerName, otherName, date);
	}

	@Test
	public void givenOwnerIdAndOtherIdAndRelationThenSaveIt() {
		Date date = new Date();
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(GregorianCalendar.DAY_OF_MONTH, 12);
		String ownerName = "owner";
		String otherName = "otherName";
		Relationship relationship = new Relationship();
		relationship.setCharacterName(otherName);
		List<Relation> relations = new ArrayList<>();
		Relation relation = new Relation();
		relation.setDate(date);
		// relations.add(relation);
		Relation relation2 = new Relation();
		relation2.setDate(gc.getTime());
		relations.add(relation2);
		relationship.setRelation(relations);

		Mockito.when(relationshipService.get(ownerName, otherName)).thenReturn(relationship);

		ArgumentCaptor<Relationship> relationshipCaptor = ArgumentCaptor.forClass(Relationship.class);
		Relation relationActual = relationService.save(ownerName, otherName, relation);

		Mockito.verify(relationshipService).save(Mockito.eq(ownerName), relationshipCaptor.capture());

		Relationship relationshipCaptured = relationshipCaptor.getValue();

		assertNotNull(relationActual);
		assertTrue(relationshipCaptured.getRelation().stream().anyMatch(p -> date.equals(p.getDate())));
	}

	@Test
	public void givenOwnerIdAndOtherIdAndRelationThenDeleteIt() {
		Date date = new Date();
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(GregorianCalendar.DAY_OF_MONTH, 12);
		String ownerName = "owner";
		String otherName = "otherName";
		Relationship relationship = new Relationship();
		relationship.setCharacterName(otherName);
		List<Relation> relations = new ArrayList<>();
		Relation relation = new Relation();
		relation.setDate(date);
		// relations.add(relation);
		Relation relation2 = new Relation();
		relation2.setDate(gc.getTime());
		relations.add(relation2);
		relationship.setRelation(relations);

		Mockito.when(relationshipService.get(ownerName, otherName)).thenReturn(relationship);

		ArgumentCaptor<Relationship> relationshipCaptor = ArgumentCaptor.forClass(Relationship.class);
		relationService.delete(ownerName, otherName, date);

		Mockito.verify(relationshipService).save(Mockito.eq(ownerName), relationshipCaptor.capture());

		Relationship relationshipCaptured = relationshipCaptor.getValue();
		assertFalse(relationshipCaptured.getRelation().stream().anyMatch(p -> date.equals(p.getDate())));
	}

}
