package com.in28minutes.spring.aop.aop.aspect;

import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonJoinPointConfig {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Pointcut("execution(* com.in28minutes.spring.aop.aop.data.*.*(..))")
	public void dataLayerExecution() {}
	
	@Pointcut("execution(* com.in28minutes.spring.aop.aop.business.*.*(..))")
	public void businessLayerExecution() {}
	
	//위의 두개의 pointcut을 &&로 같이 사용 가능
	@Pointcut("com.in28minutes.spring.aop.aop.aspect.CommonJoinPointConfig.dataLayerExecution() && com.in28minutes.spring.aop.aop.aspect.CommonJoinPointConfig.businessLayerExecution()")
	public void allLayerExecution() {}
	
	//bean 이름으로도 가능하다.
	@Pointcut("bean(dao*)")
	public void beanContainingDao() {}
	
	//패키지 내의(계층내의) 모든 호출도 intercept가 가능하다.
	@Pointcut("within(com.in28minutes.spring.aop.aop.data..*)")
	public void dataLayerExecutionWithWithin() {}
	
	@Pointcut("@annotation(com.in28minutes.spring.aop.aop.aspect.TrackTime)")
	public void trackTimeAnnotation() {}
}
