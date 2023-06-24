package com.dyonyon.spring.basics.springin5steps.cdi;

import javax.inject.Inject;
import javax.inject.Named;

// 1. Spring Version
// @Component
// 2. CDI Version
@Named
public class SomeCDIBusiness {
	// 1. Spring Version
	//	@Autowired
	// 2. CDI Version
	@Inject
	SomeCDIDAO someCDIDAO;

	public SomeCDIDAO getSomeCDIDAO() {
		return someCDIDAO;
	}

	public void setSomeCDIDAO(SomeCDIDAO someCDIDAO) {
		this.someCDIDAO = someCDIDAO;
	}
	
	public int findGreatest() {
		int greatest = Integer.MIN_VALUE;
		int[] data = someCDIDAO.getData();
		for(int value: data) {
			if(value > greatest) {
				greatest = value;
			}
		}
		return greatest;
	}
	

}
