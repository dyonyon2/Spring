package com.dyonyon.spring.basics.springin5steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.dyonyon.spring.basics.springin5steps.scope.PersonDAO;

@SpringBootApplication
public class SpringIn5StepsScopeApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(SpringIn5StepsScopeApplication.class);

	public static void main(String[] args) {
		// 1. Complex Scope! @Autowired되는 Bean은 어떻게 처리 되는지?
		// 1-1. DAO&JDBC의 Scope가 singleton일 때, Bean과 @Autowired된 Bean은 동일하다.
		// 1-2. DAO의 Scope가 prototype일 때, DAO는 다른 Bean이지만, @Autowired된 JDBCConnection은 같은 Bean이다.
		// 1-3. JDBC의 Scope가 prototype일 때, DAO는 같은 Bean이고, @Autowired된 JDBCConnection도 같은 Bean이다.
		//   -> JDBC는 prototype이지만, 같은 DAO Bean에서 호출되어 만들어진 것이기에 JDBC도 동일하게 된다.
		//   => 이와같은 경우에는 1.4와 같이 Proxy를 사용해야한다.
		// 1-4. JDBC의 Scope가 prototype이고 proxy를 사용일 때, 사용되는 모든 곳에서 프록시를 통해 새로운 JDBC를 만든다.
		//   -> 의존성에 대한(@Autowired 된 Bean) Bean을 매번 새로운 Bean을 얻기 위해서는 프록시를 사용해야 한다.
		//   => 이 경우에는 DAO1과 DAO2가 다른 JDBC를 호출하고, DAO1에서도 다른 JDBC를 호출한다. (DAO1=DAO2인 상황이다.)
		//   => DAO가 JDBC와 연결되는 것이 아니라, Proxy에 연결이 되어서, JDBC를 요청할 때마다 새로운 JDBC를 반환하는 것이다.
		ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringIn5StepsScopeApplication.class,
				args);
		PersonDAO personDAO = applicationContext.getBean(PersonDAO.class);
		PersonDAO personDAO2 = applicationContext.getBean(PersonDAO.class);

		LOGGER.info("{}", personDAO);
		LOGGER.info("{}", personDAO.getJdbcConnection());
		
		LOGGER.info("{}", personDAO);
		LOGGER.info("{}", personDAO.getJdbcConnection());
		
		LOGGER.info("{}", personDAO2);
		LOGGER.info("{}", personDAO2.getJdbcConnection());
		
		// 2. GOF singleton vs Spring singleton
		// GOF : one singleton per one JVM => 하나의 JVM에서 하나의 인스턴스(Bean)만 가질 수 있다. = 하나의 JVM, 여러 컨텍스트에서 하나만 가질수 있음
		// Spring : one singleton per application context => 하나의 application context에서 하나의 인스턴스(Bean)를 가질수 있다. = 여러 컨텍스트에서 여러 인스턴스를 가질 수 있음
		
	}

}
