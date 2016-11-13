package com.opipo.relac.controller;

import java.util.Date;
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
import org.springframework.web.bind.annotation.RestController;

import com.opipo.relac.model.Relation;
import com.opipo.relac.service.RelationService;

@RestController
@RequestMapping("/character/{owner}/relationship/{otherCharacter}/relation")
public class RelationController {

	@Autowired
	private RelationService relationService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<Long>> list(@PathVariable("owner") String ownersName,
			@PathVariable("otherCharacter") String otherName) {
		return new ResponseEntity<List<Long>>(
				relationService.list(ownersName, otherName).stream().map(f -> f.getDate()).collect(Collectors.toList()),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/{date}", method = RequestMethod.GET)
	public ResponseEntity<Relation> get(@PathVariable("owner") String ownersName,
			@PathVariable("otherCharacter") String otherName, @PathVariable("date") Long dateLong) {
		return new ResponseEntity<Relation>(relationService.get(ownersName, otherName, new Date(dateLong)), HttpStatus.OK);
	}

	@RequestMapping(value = "/{date}", method = RequestMethod.PUT)
	public ResponseEntity save(@PathVariable("owner") String ownersName,
			@PathVariable("otherCharacter") String otherName, @PathVariable("date") Long dateLong,
			@RequestBody Relation relation) {
		Assert.isTrue(dateLong.equals(relation.getDate()), "The date is not the expected");
		relationService.save(ownersName, otherName, relation);
		return new ResponseEntity(HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/{date}", method = RequestMethod.POST)
	public ResponseEntity create(@PathVariable("owner") String ownersName,
			@PathVariable("otherCharacter") String otherName, @PathVariable("date") Long dateLong) {
		Relation relation = new Relation();
		relation.setDate(new Date(dateLong).getTime());
		relationService.save(ownersName, otherName, relation);
		return new ResponseEntity(HttpStatus.CREATED);
	}

}
