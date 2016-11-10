package com.opipo.relac.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.opipo.relac.model.Relationship;
import com.opipo.relac.service.RelationshipService;

@RestController
@RequestMapping("/character/{owner}/relationship")
public class RelationshipController {

	@Autowired
	private RelationshipService relationshipService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<Collection<Relationship>> list(@PathVariable("owner") String ownersName) {
		return new ResponseEntity<Collection<Relationship>>(relationshipService.list(ownersName), HttpStatus.OK);
	}

	@RequestMapping(value = "/{characterName}", method = RequestMethod.GET)
	public ResponseEntity<Relationship> get(@PathVariable("owner") String ownersName,
			@PathVariable("characterName") String otherName) {
		return new ResponseEntity<Relationship>(relationshipService.get(ownersName, otherName), HttpStatus.OK);
	}

	@RequestMapping(value = "/{characterName}", method = RequestMethod.PUT)
	public ResponseEntity save(@PathVariable("owner") String ownersName,
			@PathVariable("characterName") String otherName, @RequestBody Relationship relationship) {
		Assert.isTrue(otherName.equalsIgnoreCase(relationship.getCharacterName()), "The name is not the expected");
		relationshipService.save(ownersName, relationship);
		return new ResponseEntity(HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/{characterName}", method = RequestMethod.POST)
	public ResponseEntity create(@PathVariable("owner") String ownersName,
			@PathVariable("characterName") String otherName) {
		Relationship relationship = new Relationship();
		relationship.setCharacterName(otherName);
		relationshipService.save(ownersName, relationship);
		return new ResponseEntity(HttpStatus.CREATED);
	}

}
