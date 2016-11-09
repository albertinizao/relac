package com.opipo.relac.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.opipo.relac.RelacApplication;
import com.opipo.relac.UnitTestApplicationConfig;
import com.opipo.relac.model.Character;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = { RelacApplication.class, UnitTestApplicationConfig.class })
public class CharacterRepositoryIntegrationTest {

	@Autowired
	private CharacterRepository characterRepository;

	@Test
	public void completeOperations() {
		String name = "character's name";
		String name2 = "character's name 2";
		Character expected = new Character();
		expected.setName(name);
		assertTrue(characterRepository.findAll().isEmpty());
		characterRepository.save(expected);
		Character actual = characterRepository.findOne(name);
		assertEquals(expected.getName(), actual.getName());
		Character expected2 = new Character();
		expected2.setName(name2);
		characterRepository.save(expected2);
		Character actual2 = characterRepository.findOne(name2);
		assertEquals(expected2.getName(), actual2.getName());
		List<String> charactersNames=characterRepository.findAll().stream().map(f->f.getName()).collect(Collectors.toList());
		assertEquals(2,charactersNames.size());
		assertTrue(charactersNames.contains(name));
		assertTrue(charactersNames.contains(name2));
		characterRepository.deleteAll();
		assertTrue(characterRepository.findAll().isEmpty());
		
	}
}
