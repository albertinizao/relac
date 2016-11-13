package com.opipo.relac.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.opipo.relac.exception.NotFoundElement;
import com.opipo.relac.model.Character;
import com.opipo.relac.model.Relation;
import com.opipo.relac.model.Relationship;
import com.opipo.relac.service.CharacterService;
import com.opipo.relac.service.UserService;

@RestController
@RequestMapping("/character")
public class CharacterController {

	@Autowired
	private CharacterService characterService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Collection<String>> list() {
		return new ResponseEntity<Collection<String>>(
				characterService.list().stream().map(f -> f.getName()).collect(Collectors.toList()), HttpStatus.OK);
	}

	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Character> get(@PathVariable("name") String name) {
		Character character = characterService.get(name);
		List<Relationship> relationshipsPrevious = character.getRelationships();
		List<Relationship> relationships = new ArrayList<>();
		for (Relationship relationship : relationshipsPrevious) {
			List<Relation> relationPrevious = relationship.getRelation();
			List<Relation> relations = new ArrayList<>();
			Relation firstRelation = relationPrevious.stream().sorted((t,o)->o.getDate().compareTo(t.getDate())).findFirst().orElse(null);
			relations.add(firstRelation);
			relationship.setRelation(relations);
			relationships.add(relationship);
		}
		character.setRelationships(relationships);
		return new ResponseEntity<Character>(character, HttpStatus.OK);
	}

	@RequestMapping(value = "/{name}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity save(@PathVariable("name") String name, @RequestBody Character character) {
		Assert.isTrue(name.equalsIgnoreCase(character.getName()), "The name is not the expected");
		Assert.notNull(characterService.get(name), "The character doesn't exists");
		characterService.save(character);
		return new ResponseEntity(HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/{name}", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity create(@PathVariable("name") String name) {
		System.out.println("TO POST");
		Character character = new Character();
		character.setName(name);
		try {
			Assert.isNull(characterService.get(name), "The character is already created");
		} catch (NotFoundElement nfe) {
			character.setUser(userService.getUserIdentifier());
			characterService.saveOverride(character);
		}
		return new ResponseEntity(HttpStatus.CREATED);
	}
}
