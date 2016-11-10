package com.opipo.relac.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.opipo.relac.UnitTestApplicationConfig;
import com.opipo.relac.model.Character;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { UnitTestApplicationConfig.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class CharacterRepositoryIntegrationTest {

	@Autowired
	private CharacterRepository characterRepository;

	@Autowired
	private MongoTemplate mongoTemplate;
	String name = "character's name";
	Character character=null;
	String name2 = "character's name 2";
	Character character2=null;

	@Before
	public void setUp() throws Exception {

		character = new Character();
		character.setName(name);
		mongoTemplate.insert(character);

		character2 = new Character();
		character2.setName(name2);
		mongoTemplate.insert(character2);

	}
	
	@Test
	public void get(){
		Character actual = characterRepository.findOne(name);
		assertNotNull(actual);
		assertEquals(character.getName(), actual.getName());
	}
	
	@Test
	public void save(){
		String otherName = "otherName";
		Character expected = new Character();
		expected.setName(otherName);
		Character actual = characterRepository.save(expected);
		assertNotNull(actual);
		assertEquals(otherName,actual.getName());
	}
	
	@Test
	public void list(){
		List<String> charactersNames = characterRepository.findAll().stream().map(f -> f.getName())
				.collect(Collectors.toList());
		assertEquals(2, charactersNames.size());
		assertTrue(charactersNames.contains(name));
		assertTrue(charactersNames.contains(name2));
	}
	
	@Test
	public void delete(){
		assertTrue(characterRepository.exists(name));
		characterRepository.delete(name);
		assertFalse(characterRepository.exists(name));
	}
	
	@Test
	public void deleteAll(){
		assertTrue(0L<characterRepository.count());
		characterRepository.deleteAll();
		assertEquals(0L,characterRepository.count());
	}
}
