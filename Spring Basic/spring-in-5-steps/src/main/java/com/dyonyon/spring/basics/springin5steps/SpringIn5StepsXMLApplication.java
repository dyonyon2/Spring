package com.dyonyon.spring.basics.springin5steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dyonyon.spring.basics.springin5steps.xml.XMLPersonDAO;

public class SpringIn5StepsXMLApplication {
	private static Logger LOGGER = LoggerFactory.getLogger(SpringIn5StepsXMLApplication.class);
	public static void main(String[] args) {
		// applicationContext XML로 불러오기! -> @Component, @Autowired 같은 것들을 XML에서 정리!
		try (ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml")) {
			LOGGER.info("Beans -> {}",(Object)applicationContext.getBeanDefinitionNames());
			
			XMLPersonDAO xmlPersonDAO = applicationContext.getBean(XMLPersonDAO.class);
			LOGGER.info("{}",xmlPersonDAO);
			LOGGER.info("{}",xmlPersonDAO.getXmlJdbcConnection());
		}
	}
}
