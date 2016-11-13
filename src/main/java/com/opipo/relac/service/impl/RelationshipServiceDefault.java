package com.opipo.relac.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opipo.relac.exception.NotFoundElement;
import com.opipo.relac.model.Character;
import com.opipo.relac.model.Relationship;
import com.opipo.relac.service.CharacterService;
import com.opipo.relac.service.RelationshipService;

@Service
public class RelationshipServiceDefault implements RelationshipService {

	@Autowired
	private CharacterService characterService;

	@Override
	public List<Relationship> list(String ownersName) {
		Character character = characterService.get(ownersName);
		return character.getRelationships();
	}

	@Override
	public Relationship get(String ownersName, String characterName) {
		return characterService.get(ownersName).getRelationships().stream()
				.filter(p -> characterName.equalsIgnoreCase(p.getCharacterName())).findFirst()
				.orElseThrow(() -> new NotFoundElement(
						new StringBuilder("Relationship with ").append(characterName).toString()));
	}

	@Override
	public Relationship save(String ownersName, Relationship relationship) {
		Character owner = characterService.get(ownersName);
		List<Relationship> relationships = owner.getRelationships().stream()
				.filter(p -> !relationship.getCharacterName().equalsIgnoreCase(p.getCharacterName()))
				.collect(Collectors.toList());
		relationships.add(relationship);
		owner.setRelationships(relationships);
		characterService.saveOverride(owner);
		return relationship;
	}

	@Override
	public void delete(String ownersName, String characterName) {
		Character owner = characterService.get(ownersName);
		List<Relationship> relationships = owner.getRelationships().stream()
				.filter(p -> !characterName.equalsIgnoreCase(p.getCharacterName()))
				.collect(Collectors.toList());
		owner.setRelationships(relationships);
		characterService.saveOverride(owner);
	}

}
