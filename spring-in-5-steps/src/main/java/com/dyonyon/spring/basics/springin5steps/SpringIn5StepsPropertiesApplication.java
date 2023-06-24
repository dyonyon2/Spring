package com.dyonyon.spring.basics.springin5steps;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.dyonyon.spring.basics.springin5steps.basic.BinarySearchImpl;
import com.dyonyon.spring.basics.springin5steps.basic.NoBootImpl;
import com.dyonyon.spring.basics.springin5steps.properties.SomeExternalService;

@Configuration
@ComponentScan("com.dyonyon.spring.basics.springin5steps")
// app.properties
@PropertySource("classpath:app.properties")
public class SpringIn5StepsPropertiesApplication {
	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				SpringIn5StepsPropertiesApplication.class)) {
			SomeExternalService someExternalService = applicationContext.getBean(SomeExternalService.class);
			System.out.println(someExternalService);

			String result = someExternalService.returnServiceURL();
			System.out.println(result);
		}
	}
}
