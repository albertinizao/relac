package com.opipo.relac.controller;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.opipo.relac.service.CharacterService;

@RestController
@RequestMapping("/game")
public class GameController {

	@Autowired
	private CharacterService characterService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Collection<String>> list() {
		return new ResponseEntity<Collection<String>>(characterService.list(null).stream().map(f -> f.getGame())
				.distinct().filter(p -> p != null).collect(Collectors.toList()), HttpStatus.OK);
	}
}
