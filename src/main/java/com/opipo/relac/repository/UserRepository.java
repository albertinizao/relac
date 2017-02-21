package com.opipo.relac.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.opipo.relac.model.User;

public interface UserRepository extends MongoRepository<User, String> {

	public User findByUsername(String username);

}