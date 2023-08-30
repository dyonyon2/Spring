package com.in28minutes.spring.aop.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class MethodExecutionCalculationAspect {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Around("execution(* com.in28minutes.spring.aop.aop.business.*.*(..))")
	public Object around(ProceedingJoinPoint joinpoint) throws Throwable {
		// start Time = x
		// allow execution of method
		// end Time = y
		long startTime = System.currentTimeMillis();
		Object value = joinpoint.proceed();
		long timeTaken = System.currentTimeMillis()-startTime;
		
		logger.info("Time Taken by {} is {}",joinpoint,timeTaken);

		return value;
	}
	
	@Around("com.in28minutes.spring.aop.aop.aspect.CommonJoinPointConfig.trackTimeAnnotation()")
	public Object aroundTrackTime(ProceedingJoinPoint joinpoint) throws Throwable {
		// start Time = x
		// allow execution of method
		// end Time = y
		long startTime = System.currentTimeMillis();
		Object value = joinpoint.proceed();
		long timeTaken = System.currentTimeMillis()-startTime;
		
		logger.info("Track Time Taken by {} is {}",joinpoint,timeTaken);

		return value;
	}
}
