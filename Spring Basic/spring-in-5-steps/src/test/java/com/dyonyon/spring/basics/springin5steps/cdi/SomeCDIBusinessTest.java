package com.dyonyon.spring.basics.springin5steps.cdi;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

@RunWith(MockitoJUnitRunner.class)
public class SomeCDIBusinessTest {
	
	//Inject Mock
	@InjectMocks
	SomeCDIBusiness business;
	
	//Create Mock
	@Mock
	SomeCDIDAO daoMock;
	
	@Test
	public void testBasicSenario() {
		Mockito.when(daoMock.getData()).thenReturn(new int[] {2,4});
		//when daoMock.getData() method is calles return in[] {2,4}
		int actualResult = business.findGreatest();
		assertEquals(4, actualResult);
	}

}
