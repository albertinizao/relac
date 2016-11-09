package com.opipo.relac.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import com.opipo.relac.model.Character;

@Service
public interface CharacterRepository  extends MongoRepository<Character, String> {

}
