package com.dyonyon.spring.basics.springin5steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.dyonyon.spring.basics.componentscan.ComponentDAO;
import com.dyonyon.spring.basics.springin5steps.scope.PersonDAO;

@SpringBootApplication
@ComponentScan("com.dyonyon.spring.basics.componentscan")
@ComponentScan("com.dyonyon.spring.basics.springin5steps")
public class SpringIn5StepsComponentScanApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(SpringIn5StepsComponentScanApplication.class);

	public static void main(String[] args) {
		// 1-1. 다른 패키지에 있는 Bean을 사용하면 ERROR가 발생한다. => SpringBootApplication이 속한 패키지와 그 하위 패키지들의 Bean을 찾아서 만들기 때문이다.
		//  -> Default 값인 @ComponentScan("com.dyonyon.spring.basics.springin5steps") 가 생략이 되어 있는 것이다.
		// 1-2. 다른 패키지를 바라보도록 다른 패키지의 주소를 넣어 @ComponentScan("com.dyonyon.spring.basics.componentscan")를 작성한다.
		//  -> @ComponentScan()한 주소 및 그 하위 패키지의 컴포넌트(Bean)들을 사용할 수 있게 됨.
		// 1-3. @ComponentScan()를 한 뒤에, 원래 default였던 패키지의 Bean에 접근하면 에러 발생.
		//  -> @ComponentScan() 추가하면 여러 패키지 컴포넌트 사용 가능함.
		
		
		// 1-2
		ConfigurableApplicationContext applicationContext = SpringApplication
				.run(SpringIn5StepsComponentScanApplication.class, args);
		ComponentDAO componentDAO = applicationContext.getBean(ComponentDAO.class);
		LOGGER.info("{}", componentDAO);
		
		// 1-3
		PersonDAO personDAO = applicationContext.getBean(PersonDAO.class);
		LOGGER.info("{}", personDAO);
	}

}
