package com.opipo.relac.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Character {
	@Id
	private String name;
	@Indexed
	private String game;
	private String user;
	private List<Relationship> relationships;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public List<Relationship> getRelationships() {
		return relationships==null?new ArrayList<>():new ArrayList<>(relationships);
	}
	public void setRelationships(List<Relationship> relationships) {
		this.relationships = relationships==null?null:new ArrayList<>(relationships);
	}
	public String getGame() {
		return game;
	}
	public void setGame(String game) {
		this.game = game;
	}
	
	
	
}
