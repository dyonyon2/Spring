package com.dyonyon.spring.basics.springin5steps.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.dyonyon.spring.basics.springin5steps.SpringIn5StepsComponentScanApplication;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;


@Service // **Business Layer라서 @Service! Component도 사용 가능
public class BinarySearchImpl2 {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());  

	@Autowired
	@Qualifier("bubble")
	private SortAlgorithm sortAlgorithm;
	
	public int binarySearch(int[] numbers, int numberToSearchFor) {
		int[] sortedNumbers3 = sortAlgorithm.sort(numbers);
		System.out.println(sortAlgorithm);
		
		return 3;
	}
	
	@PostConstruct
	public void postConstruct() {
		logger.info("IN PostConstruct!");
	}
	
	@PreDestroy
	public void preDestroy() {
		logger.info("IN PreDestroy!");
	}

}
