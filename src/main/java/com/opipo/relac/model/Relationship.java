package com.opipo.relac.model;

import java.util.ArrayList;
import java.util.Collection;

public class Relationship {
	private String characterName;
	private Collection<Relation> relation;

	public String getCharacterName() {
		return characterName;
	}

	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}

	public Collection<Relation> getRelation() {
		return relation == null ? null : new ArrayList<>(relation);
	}

	public void setRelation(Collection<Relation> relation) {
		this.relation = relation == null ? null : new ArrayList<>(relation);
	}

}
