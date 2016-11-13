package com.opipo.relac.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.opipo.relac.UnitTestApplicationConfig;
import com.opipo.relac.model.Character;
import com.opipo.relac.repository.CharacterRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { UnitTestApplicationConfig.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class CharacterIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private CharacterRepository characterRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Value("${local.server.port}")
	private int port;

	private String name = "character's name";

	private String name2 = "character's name 2";

	private Character character;

	private Character character2;

	@Before
	public void setUp() {
		character = new Character();
		character.setName(name);
		mongoTemplate.insert(character);
		character2 = new Character();
		character2.setName(name2);
		mongoTemplate.insert(character2);
	}

	@Test
	public void list() {
		ResponseEntity<Collection> response = this.restTemplate.getForEntity(getBaseUrl() + "/character",
				Collection.class);
		assertNotNull(response);
		Collection<String> responseCollection = response.getBody();
		assertNotNull(responseCollection);
		assertTrue(responseCollection.contains(name));
		assertTrue(responseCollection.contains(name2));
		assertEquals(2, responseCollection.size());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	@Ignore
	public void get() {
		ResponseEntity<Character> response = this.restTemplate.getForEntity(getBaseUrl() + "/character/{name}",
				Character.class, name);
		assertNotNull(response);
		Character responseCharacter = response.getBody();
		assertNotNull(responseCharacter);
		assertEquals(name, responseCharacter.getName());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void insert() {
		String newName = "newName";
		assertNull(characterRepository.findOne(newName));
		ResponseEntity response = this.restTemplate.postForEntity(getBaseUrl() + "/character/{name}", null,
				String.class, newName);
		assertNotNull(response);
		assertEquals((String) response.getBody(), HttpStatus.CREATED, response.getStatusCode());
		assertNotNull((String) response.getBody(), characterRepository.findOne(newName));
	}

	@Test
	public void insertExistinCharacter() {
		assertNotNull(characterRepository.findOne(name));
		ResponseEntity response = this.restTemplate.postForEntity(getBaseUrl() + "/character/{name}", null,
				String.class, name);
		assertNotNull(response);
		assertEquals((String) response.getBody(), HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@Ignore
	public void update() {
		HttpEntity<Character> entity = new HttpEntity<Character>(character, null);
		HttpHeaders headers = new HttpHeaders();
		headers.add("your_header", "its_value");
		ResponseEntity response = this.restTemplate.exchange(getBaseUrl() + "/character/{name}", HttpMethod.PUT, entity,
				String.class, name);
		assertNotNull(response);
		assertEquals((String) response.getBody(), HttpStatus.ACCEPTED, response.getStatusCode());
	}

	@Test
	@Ignore
	public void updateInconexRequest() {
		String newName = "newName";
		HttpEntity<Character> entity = new HttpEntity<Character>(character, null);
		ResponseEntity response = this.restTemplate.exchange(getBaseUrl() + "/character/{name}", HttpMethod.PUT, entity,
				String.class, newName);
		assertNotNull(response);
		assertEquals((String) response.getBody(), HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@Ignore
	public void updateInexistentCharacter() {
		String newName = "newName";
		Character otherCharacter = new Character();
		otherCharacter.setName(newName);
		HttpEntity<Character> entity = new HttpEntity<Character>(otherCharacter, null);
		ResponseEntity response = this.restTemplate.exchange(getBaseUrl() + "/character/{name}", HttpMethod.PUT, entity,
				String.class, newName);
		assertNotNull(response);
		assertEquals((String) response.getBody(), HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	private String getBaseUrl() {
		return new StringBuilder("http://localhost:").append(port).toString();
	}

}
