package com.in28minutes.spring.aop.aop.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in28minutes.spring.aop.aop.data.Dao1;

@Service
public class Business1 {
	
	@Autowired
	private Dao1 dao1;
	public String calculateSomething() {
		return dao1.retrieveSomething();
	}
}
