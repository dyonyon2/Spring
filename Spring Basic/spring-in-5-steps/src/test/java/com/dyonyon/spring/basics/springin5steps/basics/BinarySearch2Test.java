package com.dyonyon.spring.basics.springin5steps.basics;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.dyonyon.spring.basics.springin5steps.SpringIn5StepsBasicApplication2;
import com.dyonyon.spring.basics.springin5steps.basic.BinarySearchImpl2;

//Load the context
@RunWith(SpringRunner.class)
@ContextConfiguration(classes=SpringIn5StepsBasicApplication2.class)
public class BinarySearch2Test {

	//Get this bean from the context
	@Autowired
	BinarySearchImpl2 binarySearch;
	@Test
	public void testBasicSenario() {
		int actualResult = binarySearch.binarySearch(new int[] {}, 5);
		assertEquals(3, actualResult);
		// call method on binarySearch
		// check if the value is correct
		
		
	}

}
