package com.opipo.relac;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.opipo.relac.model.Character;
import com.opipo.relac.model.Relation;
import com.opipo.relac.model.Relationship;
import com.opipo.relac.model.User;
import com.opipo.relac.model.UserRole;
import com.opipo.relac.repository.CharacterRepository;
import com.opipo.relac.repository.UserRepository;

@SpringBootApplication
public class RelacApplication extends WebSecurityConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(RelacApplication.class, args);
	}

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}

	@Bean
	CommandLineRunner init(final CharacterRepository characterRepository, UserRepository userRepository) {

		return new CommandLineRunner() {

			@Override
			public void run(String... arg0) throws Exception {		

				User user = new User();
				user.setUsername("admin");
				user.setPassword("$2a$10$YxuJA8ioIyawt5C/nd9wfeCNB7etZ.iXfK2bTDemsbeBkgbkoJrya");
				user.grantRole(UserRole.ADMIN);
				userRepository.save(user);

			}
		};

	}

	@Bean
	public Filter characterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return characterEncodingFilter;
	}
}
