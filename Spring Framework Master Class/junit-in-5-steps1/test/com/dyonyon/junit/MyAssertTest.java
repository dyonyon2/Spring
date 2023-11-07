package com.dyonyon.junit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class MyAssertTest {

	List<String> todos = Arrays.asList("AWS", "Azure", "DevOps");

	@Test
	void test() {
		boolean test = todos.contains("AWS");
		boolean test2 = todos.contains("GCP");
//		assertEquals(test, true);
		assertTrue(test);
		assertFalse(test2);
		assertNull(null);
		assertNotNull(todos);
		assertEquals(3,  todos.size());
		assertEquals(3,  todos.size(), "Error Message");
		assertArrayEquals(new int[] {1,2}, new int[] {2,1});
	}

}
