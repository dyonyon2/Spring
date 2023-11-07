package com.in28minutes.database.databasedemo;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.in28minutes.database.databasedemo.entity.Person;
import com.in28minutes.database.databasedemo.jdbc.PersonJdbcDAO;

//@SpringBootApplication
public class SpringJdbcDemoApplication implements CommandLineRunner{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	PersonJdbcDAO dao;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringJdbcDemoApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		logger.info("All users -> {}",dao.findAll());
		logger.info("User id 10001 users -> {}",dao.findById(10001));
		logger.info("Delete id 10002 users -> Number of Rows Deleted {}",dao.deleteById(10002));
		logger.info("All users -> {}",dao.findAll());
		logger.info("Inserting 10004 -> {}",dao.insert(new Person(10004,"JongYoung","Suwon",new Date())));
		logger.info("Update 10003 -> {}",dao.update(new Person(10003, "Sojoong24", "dongtan",new Date())));
		logger.info("All users -> {}",dao.findAll());
	}

}
