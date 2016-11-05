package com.opipo.relac.model;

import java.util.Collection;

public class Character {
	private String name;
	private Collection<Relationship> relationships;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Collection<Relationship> getRelationships() {
		return relationships;
	}
	public void setRelationships(Collection<Relationship> relationships) {
		this.relationships = relationships;
	}
	
	
}
