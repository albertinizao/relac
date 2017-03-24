package com.opipo.relac.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.opipo.relac.model.Character;

@Repository
public interface CharacterRepository extends MongoRepository<Character, String> {
	List<Character> findByGame(String game);

}
