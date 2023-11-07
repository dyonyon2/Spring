package com.in28minutes.spring.aop.aop.data;

import org.springframework.stereotype.Repository;

import com.in28minutes.spring.aop.aop.aspect.TrackTime;

@Repository
public class Dao1 {
	
	// 성능 추적을 위해 annotation 만들기
	@TrackTime
	public String retrieveSomething() {
		return "Dao1";
	}
}
