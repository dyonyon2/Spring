package com.dyonyon.spring.basics.springin5steps.cdi;

import javax.inject.Named;

//1. Spring Version
//@Component
@Named
public class SomeCDIDAO {

	public int[] getData() {
		return new int[] {5,89,100};
	}
}
