package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	@Test
	public void testListOfUsers() {
		Iterable<User> listUsers=userRepository.findAll();
		listUsers.forEach(user->System.out.println(user));
	}
	
	@Test
	public void testGetUserById() {
		User userName=userRepository.findById(1).get();
		System.out.println(userName);
		assertThat(userName).isNotNull();
	}
	@Test
	public void testUpdateUserDetails() {
		User userName=userRepository.findById(1).get();
		userName.setEnabled(true);
		userName.setEmail("khatrinaresh@gmail.com");
		userRepository.save(userName);
	}
	@Test
	public void testUpdateUserRoles() {
		User userGovinda=userRepository.findById(3).get();
		Role roleEditor=new Role(5);
		Role saleperson=new Role(4);
		userGovinda.getRoles().remove(roleEditor);
		userGovinda.addRole(saleperson);
		userRepository.save(userGovinda);
		
		
	}
	@Test
	public void deleteUser() {
		Integer userId=10;
		userRepository.deleteById(userId);
	}
	@Test
   public void testUserByEmail() {
	   String email="khatrinaresh20101@gmail.com";
	  User user= userRepository.getUserByEmail(email);
	   assertThat(user).isNotNull();
	   
   }
	@Test
	public void testCountById() {
		Integer id=1;
		Long countById=userRepository.countById(id);
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
	@Test
	public void testDisableStatus()
	{
		Integer id=12;
		userRepository.updateEnabledStatus(id, false);
	}
	@Test
	public void testEnableStatus() {
		Integer id=12;
		userRepository.updateEnabledStatus(id, true);
	}
	@Test
	public void testListFirstPage() {
		int pageNumber=0;
		int pageSize=4;
		Pageable pegable=PageRequest.of(pageNumber, pageSize);
		Page<User> page=userRepository.findAll(pegable);
		List<User> listUsers=page.getContent();
		listUsers.forEach(user-> System.out.println(user));
		assertThat(listUsers.size()).isEqualTo(pageSize);
		
	}
	@Test
	public void testSearchUsers() {
		String keyWord="bruce";
		int pageNumber=0;
		int pageSize=4;
		Pageable pegable=PageRequest.of(pageNumber, pageSize);
		Page<User> page=userRepository.findAll(keyWord,pegable);
		List<User> listUsers=page.getContent();
		listUsers.forEach(user-> System.out.println(user));
		assertThat(listUsers.size()).isGreaterThan(0);
		
		
	}
}
