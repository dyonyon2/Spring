package com.dyonyon.spring.basics.springin5steps;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.dyonyon.spring.basics.springin5steps.basic.BinarySearchImpl;
import com.dyonyon.spring.basics.springin5steps.basic.NoBootImpl;

// Spring Boot에서 제공하는 Context 초기화하는 방법
//@SpringBootApplication

// Spring Boot 없이 Basic Spring을 사용해서 동작시키기
@Configuration
@ComponentScan("com.dyonyon.spring.basics.springin5steps")
public class SpringIn5StepsNoBootApplication {
	public static void main(String[] args) {
		// Annotation Config Application Context (ACAC)
		
		// applicationContext 사용후 닫는 방법 
		// 1. close() 사용
		// 2. try(){} 사용
		try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				SpringIn5StepsNoBootApplication.class)) {
			NoBootImpl noBootImpl = applicationContext.getBean(NoBootImpl.class);
			System.out.println(noBootImpl);

			int result = noBootImpl.binarySearch(new int[] { 12, 4, 6 }, 3);
			System.out.println(result);
		}
	}
}
