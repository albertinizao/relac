package com.opipo.relac.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Character {
	@Id
	private String name;
	private List<Relationship> relationships;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Relationship> getRelationships() {
		return relationships==null?new ArrayList<>():new ArrayList<>();
	}
	public void setRelationships(List<Relationship> relationships) {
		this.relationships = relationships==null?null:new ArrayList<>(relationships);
	}
	
	
}
