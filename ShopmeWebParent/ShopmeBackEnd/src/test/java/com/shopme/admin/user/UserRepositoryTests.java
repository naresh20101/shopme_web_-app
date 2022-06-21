package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TestEntityManager testEntityManager;
	@Test
	public void testcreateNewUserWithOneRole() {
		Role roleAdmin=testEntityManager.find(Role.class, 2);
		User userNaresh=new User("khatrinaresh20101@gmail.com", "cs2020", "Naresh", "Kumar");
		userNaresh.addRole(roleAdmin);
		 User savedUser=userRepository.save(userNaresh);
		assertThat(savedUser.getId()).isGreaterThan(0);
		
	}
	@Test
	public void testCreateNewUserWithTwoRoles() {
		User userGovinda=new User("govinda37@gmail.com", "cs1837", "Govinda","Kohli");
		Role roleEditor=new Role(5);
		Role roleAssistant=new Role(7);
		 userGovinda.addRole(roleEditor);
		 userGovinda.addRole(roleAssistant);
		 User savedUser=userRepository.save(userGovinda);
		 assertThat(savedUser.getId()).isGreaterThan(0);
				 
		
		
	}

}
