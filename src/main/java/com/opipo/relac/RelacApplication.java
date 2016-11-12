package com.opipo.relac;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.context.request.RequestContextListener;

import com.opipo.relac.model.Character;
import com.opipo.relac.model.Relation;
import com.opipo.relac.model.Relationship;
import com.opipo.relac.repository.CharacterRepository;

@SpringBootApplication
@EnableOAuth2Sso
public class RelacApplication extends WebSecurityConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(RelacApplication.class, args);
	}

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}

	@Bean
	CommandLineRunner init(final CharacterRepository characterRepository) {

		return new CommandLineRunner() {

			@Override
			public void run(String... arg0) throws Exception {
				Character character = new Character();
				character.setName("Manolete");
				Relationship relationship1 = new Relationship();
				relationship1.setCharacterName("Paquito");
				List<Relation> relations1 = new ArrayList<>();
				Relation relation11 = new Relation();
				relation11.setDate(new Date());
				relation11.setAffection(12);
				relations1.add(relation11);
				relationship1.setRelation(relations1);
				List<Relationship> relationships = new ArrayList<>();
				relationships.add(relationship1);
				character.setRelationships(relationships);
				characterRepository.delete(character);
				characterRepository.save(character);

			}

		};

	}
}
