package com.in28minutes.spring.aop.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class AfterAopAspect {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//Method가 성공적으로 실행되어 리턴되었을 때
	@AfterReturning(
			value="execution(* com.in28minutes.spring.aop.aop.business.*.*(..))",
			returning="result"
			)
	public void after(JoinPoint joinpoint, Object result) {
		logger.info("{} return with value {}",joinpoint, result);
	}
	
	
	//Method 실행이 오류 발생하여 실패했을 때
	@AfterThrowing(
			value="execution(* com.in28minutes.spring.aop.aop.business.*.*(..))",
			throwing="exception"
			)
	public void after1(JoinPoint joinpoint, Exception exception) {
		logger.info("{} throw exception {}",joinpoint,exception);
	}
	
	
	//Method 실행의 성공 여부에 관계없이 실행
	@After(
			value="execution(* com.in28minutes.spring.aop.aop.business.*.*(..))"
			)
	public void after2(JoinPoint joinpoint) {
		logger.info("after execution of {}",joinpoint);
	}
}
