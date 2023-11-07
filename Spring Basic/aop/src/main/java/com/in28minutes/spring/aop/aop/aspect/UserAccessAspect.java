package com.in28minutes.spring.aop.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

//AOP
@Aspect
//Configuration
@Configuration
public class UserAccessAspect {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//What kind of method calls I would intercept
	//execution(* PACKAGE.*.*(..))
	
	// 일반적인 pointcut 입력
//	@Before("execution(* com.in28minutes.spring.aop.aop.data.*.*(..))")
	// 공통적인 pointcut을 class로 만들어 @Pointcut을 사용하여 공통적으로 사용하게 변경
	@Before("com.in28minutes.spring.aop.aop.aspect.CommonJoinPointConfig.dataLayerExecution()")
	public void before(JoinPoint joinpoint) {
		//What to do?
//		logger.info(" Intercepted Method Calls - {}",joinpoint);
		
		// User access checking
		logger.info(" Check for user access ");
		logger.info(" Allowed execution for {}",joinpoint);
	}
	
//	// aop 하위 패키지의 모든 메소드에 대해서 intercept된다.
//	@Before("execution(* com.in28minutes.spring.aop.aop..*.*(..))")
//	public void before1(JoinPoint joinpoint) {
//		// User access checking
//		logger.info(" Check for user access ");
//		logger.info(" Allowed execution for {}",joinpoint);
//	}
}
