package com.dyonyon.junit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MyMathTest {
	
	MyMath math = new MyMath();

	@Test
	void calculateSum_FourMemberArray() {
		// 테스트 코드 정리 후  (Refactor -> Inline 활용)
		assertEquals(math.calculateSum(new int[] {1,2,3,4}), 10);
		
		/*  => 테스트 코드 정리 전
		// Absence of failure is success
		// fail("Not yet implemented");
		MyMath math = new MyMath();
		int result = math.calculateSum(new int[] {1,2,3,4});
		System.out.println(result);
		int expectedResult = 10;
		assertEquals(result, expectedResult);	
		//  => 동일하면 통과, 불일치시 Fail
//		assertEquals(result, expectedResult+1);
		*/
	}
	
	@Test
	void calculateSum_ZeroMemberArray() {
		assertEquals(math.calculateSum(new int[] {}), 0);
	}

}
