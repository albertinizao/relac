package com.opipo.relac.model;

import java.util.ArrayList;
import java.util.List;

public class Relationship {
	private String characterName;
	private List<Relation> relation;

	public String getCharacterName() {
		return characterName;
	}

	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}

	public List<Relation> getRelation() {
		return relation == null ? new ArrayList<>() : new ArrayList<>(relation);
	}

	public void setRelation(List<Relation> relation) {
		this.relation = relation == null ? new ArrayList<>() : new ArrayList<>(relation);
	}

}
