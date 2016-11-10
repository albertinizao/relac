package com.opipo.relac.service;

import java.util.Date;
import java.util.List;

import com.opipo.relac.model.Relation;

public interface RelationService {
	List<Relation> list(String ownersName, String characterName);

	Relation get(String ownersName, String characterName, Date date);

	Relation save(String ownersName, String characterName, Relation relation);

	void delete(String ownersName, String characterName, Date date);

}
