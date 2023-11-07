package com.in28minutes.spring.aop.aop.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//Methods
@Target(ElementType.METHOD)
//Runtime에서도 사용 가능하도록
@Retention(RetentionPolicy.RUNTIME)
public @interface TrackTime {
	
}
