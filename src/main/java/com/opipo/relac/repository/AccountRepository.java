package com.opipo.relac.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.opipo.relac.model.Account;

public interface AccountRepository extends MongoRepository<Account, String> {

	public Account findByUsername(String username);

}	