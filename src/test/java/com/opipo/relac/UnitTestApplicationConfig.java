package com.opipo.relac;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;

@EnableAutoConfiguration(exclude = { EmbeddedMongoAutoConfiguration.class, MongoAutoConfiguration.class,
		MongoDataAutoConfiguration.class })
@Configuration
@ComponentScan(basePackages = { "com.opipo.relac" }, excludeFilters = {
		@ComponentScan.Filter(classes = { SpringBootApplication.class }) })
public class UnitTestApplicationConfig extends AbstractMongoConfiguration {
	@Autowired
	private Environment env;

	@Override
	protected String getDatabaseName() {
		return "myDB";
	}

	@Override
	public Mongo mongo() throws Exception {
		return new Fongo(getDatabaseName()).getMongo();
	}
}
