package com.shopme.admin.user;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Service
@Transactional
public class UserService {
	public static final int pageSize=4;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	public List<User> listAll(){
		return (List<User>) userRepository.findAll();
	}
	public Page<User> listByPage(int pageNum){
		Pageable pegeable=PageRequest.of(pageNum-1, pageSize);
		return userRepository.findAll(pegeable);
	}
	public List<Role> listRoles(){
		return (List<Role>) roleRepository.findAll();
	}
	public User save(User user){
		boolean isUpdatingUser=(user.getId()!=null);
		if(isUpdatingUser) {
			User exisUser=userRepository.findById(user.getId()).get();
			if(user.getPassword().isEmpty()) {
				user.setPassword(exisUser.getPassword());
			}
			else {
				encodePassword(user);
			}
		}
		else{
		encodePassword(user);}
		return userRepository.save(user);
		
	}
	private void encodePassword(User user) {
		String endcodePassword=passwordEncoder.encode(user.getPassword());
		user.setPassword(endcodePassword);
	}
	
	public boolean isEmailUnique(Integer id, String email) {
		
		User user=userRepository.getUserByEmail(email);
		if(user==null) return true;
		boolean isCreatingNew=(id==null);
		if(isCreatingNew) {
			if(user!=null) return false;
		}
		else {
			if(user.getId()!=id) return false;
		}
		return true;
	}
	public User get(Integer id) throws UserNotFoundException {
		// TODO Auto-generated method stub
		try {
		return userRepository.findById(id).get();
		}
		catch (NoSuchElementException ex) {
              throw new UserNotFoundException("Counld not find any user with ID "+ id);
		}
		
	}
	public void deleteUser(Integer id) throws UserNotFoundException {
		Long countById=userRepository.countById(id);
		if(countById==null || countById==0) {
			throw new UserNotFoundException("Counld not find any user with ID "+ id);
		}
		userRepository.deleteById(id);
	}
	public void updateUserEnableStatus(Integer id, boolean enabled) {
		userRepository.updateEnabledStatus(id, enabled);
	}
}
