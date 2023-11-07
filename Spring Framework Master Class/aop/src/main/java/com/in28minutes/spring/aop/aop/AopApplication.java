package com.in28minutes.spring.aop.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.in28minutes.spring.aop.aop.business.Business1;
import com.in28minutes.spring.aop.aop.business.Business2;

@SpringBootApplication
public class AopApplication implements CommandLineRunner{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	Business1 business1;
	
	@Autowired
	Business2 business2;
	
	//기존에는 run 한 리턴(ConfigurableApplicationContext)을 받아서 applicationContext.getBean()을 통해서 인스턴스에 접근했다.
	public static void main(String[] args) {
		SpringApplication.run(AopApplication.class, args);
	}
	

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		logger.info(business1.calculateSomething());
		logger.info(business2.calculateSomething());
	}

}
