package com.opipo.relac.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.opipo.relac.model.Character;

public interface CharacterRepository  extends MongoRepository<Character, String> {

}
