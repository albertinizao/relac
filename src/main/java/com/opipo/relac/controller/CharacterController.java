package com.opipo.relac.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.opipo.relac.model.Character;
import com.opipo.relac.model.Relation;
import com.opipo.relac.model.Relationship;

@RestController
@RequestMapping("/character")
public class CharacterController {
	

	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Collection<String>> list() {
		Collection<String> lista = new ArrayList<>();
		lista.add("prueba1");
		lista.add("prueba2");
		return new ResponseEntity<Collection<String>>(lista, HttpStatus.OK);
	}

	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Character> get(@PathVariable("name") String name) {
		Character character = new Character();
		character.setName(name);
		Collection<Relationship> relationships = new ArrayList<>();
		Relation relation = new Relation();
		relation.setAffection(2);
		relation.setDate(new Date());
		Relationship relationship = new Relationship();
//		relationship.setCharacterName("other");
		Collection<Relation> relations = new ArrayList<>();
		relations.add(relation);
		relationship.setRelation(relations);
		relationships.add(relationship);
		character.setRelationships(relationships);
		return new ResponseEntity<Character>(character, HttpStatus.OK);
	}
}
