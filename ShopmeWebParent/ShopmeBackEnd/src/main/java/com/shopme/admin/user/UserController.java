package com.shopme.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		model.addAttribute("pageTitle","Create New User");
		return "user_form";
		
	}
	@PostMapping("/users/save")
	public String saveUser(User user ,RedirectAttributes redirectAttributes) {
		System.out.println("User details is"+ user);
		userService.save(user);
	    redirectAttributes.addFlashAttribute("message","The user have been saved sucessfully");
		return"redirect:/users";
		
	}
	
	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable("id") Integer id,RedirectAttributes redirectAttributes,Model model) {
		try {
		User user=userService.get(id);
		List<Role> listRoles =userService.listRoles();
		model.addAttribute("user",user);
		model.addAttribute("pageTitle","Edit User(ID:"+id+")");
		model.addAttribute("listRoles",listRoles);
		
		return "user_form";
		}
		catch (UserNotFoundException ex) {
			 redirectAttributes.addFlashAttribute("message",ex.getMessage());
			// TODO: handle exception
			 return"redirect:/users";
		}
		
		
	}
	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable("id") Integer id,RedirectAttributes redirectAttributes,Model model) {
		try {
	     userService.deleteUser(id);
		redirectAttributes.addFlashAttribute("message","The User ID:"+ id + " has been deleted successfully");
		}
		catch (UserNotFoundException ex) {
			 redirectAttributes.addFlashAttribute("message",ex.getMessage());
			// TODO: handle exception
			// return"redirect:/users";
		}
		 return"redirect:/users";
	}
	@GetMapping("/users/{id}/enabled/{status}")
	public String updateUserEnableStatus(@PathVariable("id") Integer id,@PathVariable("status") boolean enabled,RedirectAttributes redirectAttributes) {
		userService.updateUserEnableStatus(id, enabled);
		String status=enabled ? "enabled":"disabled";
		String message="The User ID "+ id+ "has been "+ status;
		 redirectAttributes.addFlashAttribute("message",message);
		 return"redirect:/users";
	}
	
	


}
