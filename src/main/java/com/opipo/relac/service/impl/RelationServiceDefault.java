package com.opipo.relac.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.opipo.relac.exception.NotFoundElement;
import com.opipo.relac.model.Relation;
import com.opipo.relac.model.Relationship;
import com.opipo.relac.service.RelationService;
import com.opipo.relac.service.RelationshipService;

@Service
public class RelationServiceDefault implements RelationService {

	@Autowired
	private RelationshipService relationshipService;

	@Override
	public List<Relation> list(String ownersName, String characterName) {
		return relationshipService.get(ownersName, characterName).getRelation();
	}

	@Override
	public Relation get(String ownersName, String characterName, Date date) {
		return relationshipService.get(ownersName, characterName).getRelation().stream()
				.filter(p -> date.equals(p.getDate())).findFirst()
				.orElseThrow(() -> new NotFoundElement(new StringBuilder("Relation with ").append(date).toString()));
	}

	@Override
	public Relation save(String ownersName, String characterName, Relation relation) {
		Relationship relationship = relationshipService.get(ownersName, characterName);
		List<Relation> relationsWithoutRelation = relationship.getRelation().stream()
				.filter(p -> !relation.getDate().equals(p.getDate())).collect(Collectors.toList());
		relationsWithoutRelation.add(relation);
		relationship.setRelation(relationsWithoutRelation);
		relationshipService.save(ownersName, relationship);
		return relation;
	}

	@Override
	public void delete(String ownersName, String characterName, Date date) {
		Relationship relationship = relationshipService.get(ownersName, characterName);
		List<Relation> relationsWithoutRelation = relationship.getRelation().stream()
				.filter(p -> !date.equals(p.getDate())).collect(Collectors.toList());
		relationship.setRelation(relationsWithoutRelation);
		relationshipService.save(ownersName, relationship);
	}

}
