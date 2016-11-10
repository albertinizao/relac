package com.opipo.relac.service;

import java.util.List;

import com.opipo.relac.model.Relationship;

public interface RelationshipService {
	List<Relationship> list(String ownersName);

	Relationship get(String ownersName, String characterName);

	Relationship save(String ownersName, Relationship relationship);

	void delete(String ownersName, String characterName);
}
