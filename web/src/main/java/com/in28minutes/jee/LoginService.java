package com.in28minutes.jee;

import org.springframework.stereotype.Service;

@Service
public class LoginService {
	
	public boolean isUserValid(String user, String password) {
		if (user.equals("dyon")&&password.equals("dyon PW"))
			return true;
		else 
			return false;
	}
}
