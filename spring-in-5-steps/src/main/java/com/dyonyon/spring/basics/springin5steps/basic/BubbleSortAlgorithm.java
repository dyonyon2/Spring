package com.dyonyon.spring.basics.springin5steps.basic;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@Component
@Service // **Business Layer라서 @Service! Component도 사용 가능
// 4-1. (3) @Qualifier("식별자")를 붙인다.
@Qualifier("bubble")
public class BubbleSortAlgorithm implements SortAlgorithm{
	public int[] sort(int[] numbers) {
		//Logic for Bubble Sort
		return numbers;
	}
}
