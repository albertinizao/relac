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
//@EnableOAuth2Sso
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
				Character character = new Character();
				character.setName("Manolete");
				Relationship relationship1 = new Relationship();
				relationship1.setCharacterName("Paquito");
				List<Relation> relations1 = new ArrayList<>();
				Relation relation11 = new Relation();
				relation11.setDate(new Date().getTime());
				relation11.setWorking(8);
				relation11.setConfidential(4);
				relation11.setLoyalty(6);
				relation11.setTrust(6);
				relation11.setRespect(7);
				relation11.setFunny(5);
				relation11.setAffection(6);
				relations1.add(relation11);
				Relation relation12 = new Relation();
				relation12.setDate(1486934646908L);
				relation12.setWorking(7);
				relation12.setConfidential(3);
				relation12.setLoyalty(5);
				relation12.setTrust(5);
				relation12.setRespect(6);
				relation12.setFunny(4);
				relation12.setAffection(5);
				relations1.add(relation12);
				relationship1.setRelation(relations1);
				List<Relationship> relationships = new ArrayList<>();
				relationships.add(relationship1);
				character.setRelationships(relationships);
				characterRepository.delete(character);
				characterRepository.save(character);
				

				User user = new User();
				user.setUsername("user");
				user.setPassword(new BCryptPasswordEncoder().encode("user"));
				user.grantRole("user".equals("admin") ? UserRole.ADMIN : UserRole.USER);
				userRepository.save(user);
				

				user = new User();
				user.setUsername("admin");
				user.setPassword(new BCryptPasswordEncoder().encode("admin"));
				user.grantRole("admin".equals("admin") ? UserRole.ADMIN : UserRole.USER);
				userRepository.save(user);

			}

		};

	}
//
//	@Bean
//	public InitializingBean insertDefaultUsers() {
//		return new InitializingBean() {
//			@Autowired
//			private UserRepository userRepository;
//
//			@Override
//			public void afterPropertiesSet() {
//				addUser("admin", "admin");
//				addUser("user", "user");
//			}
//
//			private void addUser(String username, String password) {
//				User user = new User();
//				user.setUsername(username);
//				user.setPassword(new BCryptPasswordEncoder().encode(password));
//				user.grantRole(username.equals("admin") ? UserRole.ADMIN : UserRole.USER);
//				userRepository.save(user);
//			}
//		};
//	}

	@Bean
	public Filter characterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return characterEncodingFilter;
	}


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**");
            }
        };
    }
}
