package com.opipo.relac.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.opipo.relac.RelacApplication;
import com.opipo.relac.UnitTestApplicationConfig;
import com.opipo.relac.model.Character;
import com.opipo.relac.repository.CharacterRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = { RelacApplication.class, UnitTestApplicationConfig.class })
public class CharacterIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private CharacterRepository characterRepository;
	
	private String name = "character's name";
	
	private String name2 = "character's name 2";
	
	private Character character;
	
	private Character character2;
	
	@Before
	public void init(){
		 character = new Character();
		character.setName(name);
		characterRepository.insert(character);
		character2 = new Character();
		character2.setName(name2);
		characterRepository.insert(character2);
	}
	
	@After
	public void end(){
		characterRepository.deleteAll();
	}

	@Test
	public void list() {
		ResponseEntity<Collection> response = this.restTemplate.getForEntity("/character", Collection.class);
		assertNotNull(response);
		Collection<String> responseCollection = response.getBody();
		assertNotNull(responseCollection);
		assertTrue(responseCollection.contains(name));
		assertTrue(responseCollection.contains(name2));
		assertEquals(2,responseCollection.size());
		assertEquals(HttpStatus.OK,response.getStatusCode());
	}

	@Test
	public void get() {
		ResponseEntity<Character> response = this.restTemplate.getForEntity("/character/{name}", Character.class, name);
		assertNotNull(response);
		Character responseCharacter = response.getBody();
		assertNotNull(responseCharacter);
		assertEquals(name,responseCharacter.getName());
		assertEquals(HttpStatus.OK,response.getStatusCode());
	}

	@Test
	public void insert() {
		String newName = "newName";
		assertNull(characterRepository.findOne(newName));
		ResponseEntity response = this.restTemplate.postForEntity("/character/{name}", null, Character.class, newName);
		assertNotNull(response);
		assertNotNull(characterRepository.findOne(newName));
		assertEquals(HttpStatus.CREATED,response.getStatusCode());
	}

	@Test
	public void insertExistinCharacter() {
		assertNotNull(characterRepository.findOne(name));
		ResponseEntity response = this.restTemplate.postForEntity("/character/{name}", null, Character.class, name);
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
	}

	@Test
	public void update() {
	    HttpEntity<Character> entity = new HttpEntity<Character>(character, null); 
		ResponseEntity response = this.restTemplate.exchange("/character/{name}", HttpMethod.PUT, entity, Character.class, name);
		assertNotNull(response);
		assertEquals(HttpStatus.ACCEPTED,response.getStatusCode());
	}

	@Test
	public void updateInconexRequest() {
		String newName = "newName";
	    HttpEntity<Character> entity = new HttpEntity<Character>(character, null); 
		ResponseEntity response = this.restTemplate.exchange("/character/{name}", HttpMethod.PUT, entity, Character.class, newName);
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
	}

	@Test
	public void updateInexistentCharacter() {
		String newName = "newName";
		Character otherCharacter = new Character();
		otherCharacter.setName(newName);
	    HttpEntity<Character> entity = new HttpEntity<Character>(otherCharacter, null); 
		ResponseEntity response = this.restTemplate.exchange("/character/{name}", HttpMethod.PUT, entity, Character.class, newName);
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
	}

}
