package com.dyonyon.junit;

public class MyMath {
	// {1,2,3} => 1+2+3 = 6 return
	public int calculateSum(int[] numbers) {
		int sum = 0;
		for (int number : numbers) {
			sum += number;
		}
		return sum;
	}
}
