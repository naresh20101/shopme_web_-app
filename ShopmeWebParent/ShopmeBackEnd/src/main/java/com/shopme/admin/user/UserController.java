package com.shopme.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public String listAll(Model model) {
		List<User> listUsers=userService.listAll();
		model.addAttribute("listUsers",listUsers);
		return "users";
	}
	@GetMapping("/users/new")
	public String newUser(Model model) {
		List<Role> listRoles =userService.listRoles();
		
		User user=new User();
		user.setEnabled(true);
		model.addAttribute("user",user);
		model.addAttribute("listRoles",listRoles);
		return "user_form";
		
	}
	@PostMapping("/users/save")
	public String saveUser(User user ,RedirectAttributes redirectAttributes) {
		System.out.println("User details is"+ user);
		userService.save(user);
	    redirectAttributes.addFlashAttribute("message","The user have been saved sucessfully");
		return"redirect:/users";
		
	}
	public String checkDuplicateEmail(@Param("email") String email) {
		return userService.isEmailUnique(email) ? "OK": "Duplicate";
	}

}
