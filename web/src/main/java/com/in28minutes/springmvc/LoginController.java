package com.in28minutes.springmvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.in28minutes.jee.LoginService;

@Controller
public class LoginController {
	
	@Autowired
	LoginService service;

	@RequestMapping(value="/login", method=RequestMethod.GET)
	//@ResponseBody //Dispatcher Controller가 아니라 직접 응답을 return할 때!
	public String showLoginPage() {
//		return "Hello World";
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	//@ResponseBody //Dispatcher Controller가 아니라 직접 응답을 return할 때!
	public String handleLoginRequest(@RequestParam String name, @RequestParam String password, ModelMap model) {
		if(!service.isUserValid(name, password)) {
			model.put("errorMessage", "Invalid Credentials");
			return "login";
		}
		model.put("name", name);
		model.put("password", password);
		return "welcome";
	}
}
