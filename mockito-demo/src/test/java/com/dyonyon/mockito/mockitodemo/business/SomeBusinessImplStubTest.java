package com.dyonyon.mockito.mockitodemo.business;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SomeBusinessImplStubTest {

	@Test
	void findTheGreatestFromAllData_basicScenario() {
		DataService dataServiceStub1 = new DataServiceStub1();
		SomeBusinessImpl businessImpl1 = new SomeBusinessImpl(dataServiceStub1);
		assertEquals(25, businessImpl1.findTheGreatestFromAllData());
	}
	
	@Test
	void findTheGreatestFromAllData_secondScenario() {
		DataService dataServiceStub2 = new DataServiceStub1();
		SomeBusinessImpl businessImpl2 = new SomeBusinessImpl(dataServiceStub2);
		assertEquals(25, businessImpl2.findTheGreatestFromAllData());
	}

}

class DataServiceStub1 implements DataService {
	@Override
	public int[] retrieveAllData() {
		// TODO Auto-generated method stub
		return new int[] { 25, 15, 5 };
	}
}

class DataServiceStub2 implements DataService {
	@Override
	public int[] retrieveAllData() {
		// TODO Auto-generated method stub
		return new int[] { 25, 15, 5 };
	}
}

