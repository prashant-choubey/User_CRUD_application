package com.accenture.springboot.rest.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.accenture.springboot.rest.Models.User;
import com.accenture.springboot.rest.service.UserService;

@Controller
@RequestMapping
public class DemoController {
	
	@Autowired
	UserService userservice;
	
	//loading user form	
	@RequestMapping(value="/user")
	public String loadForm(@ModelAttribute("updateMessage") String updateMessage,@ModelAttribute("deleteMessage") String deleteMessage,Model m)
	{
		m.addAttribute("update",false);
		m.addAttribute("users", userservice.findAll());
		m.addAttribute("updateMessage",updateMessage);
		m.addAttribute("deleteMessage",deleteMessage);
		return "index";
	}

	//submitting user data
	@RequestMapping(value="/adduser",method=RequestMethod.POST)
	public String addUser(@ModelAttribute("addUser")User users,Model m) {
		userservice.saveUser(users);
		m.addAttribute("update",false);
		m.addAttribute("users", userservice.findAll());
		return "index";
	}
	
	//loading update form
	@RequestMapping(value="/updateUser/{id}")
	public String loadUpdateForm(@PathVariable long id,Model m)
	{
		m.addAttribute("id", id);
		m.addAttribute("update", true);
		m.addAttribute("user", userservice.findById(id));
		m.addAttribute("users", userservice.findAll());
		return "index";
	}
	//updating userData
	@RequestMapping(value="/updateUser/updateUser/{id}",method=RequestMethod.POST)
	public String updateUser(@ModelAttribute("updateUser")User users,@PathVariable long id,RedirectAttributes ra) {
		String message=userservice.updateUser(id, users);
		if(message.equals("User updated Successfully")) {
			ra.addFlashAttribute("updateMessage", "Updated user Id: "+id+" Successfully");

		}else {
			ra.addFlashAttribute("updateMessage", "User "+id+" Update is failed");
		}
		return "redirect:/user";
	}

	//delete User Record
	@RequestMapping(value="deleteUserData")
	public String DeleteUser(Model m,HttpServletRequest request,RedirectAttributes rb)
	{
		String userIds[]=request.getParameterValues("usercheckbox");
			List<Long> userIdsLong=new ArrayList();
		    for(int i=0;i<userIds.length;i++)
		    {
		        userIdsLong.add(Long.parseLong(userIds[i]));
		    }
		    String message=userservice.deleteUsers(userIdsLong);
		    if(message.equals("Success")) {
				rb.addFlashAttribute("deleteMessage", "Deleted user Id: "+Arrays.toString(userIds)+" Successfully");

			}else {
				rb.addFlashAttribute("deleteMessage", "User "+Arrays.toString(userIds)+" Delete is failed");
			}
		return "redirect:/user";
	}
	
	//for pagination
	@RequestMapping(value="/page/{pageNo}")
	public String findPaginated(@PathVariable (value="pageNo") int pageNo,Model model) {
		int pageSize=5;
		
		Page<User> page=userservice.findPaginated(pageNo, pageSize);
		List<User> listUsers=page.getContent();
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listUsers", listUsers);
		return "index";
		
	}


}

