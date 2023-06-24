package com.dyonyon.spring.basics.springin5steps.basic;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


//@Component
@Service // **Business Layer라서 @Service! Component도 사용 가능
// 4-3. @Qualifier 설정
@Qualifier("quick")
// 3-2. 특정 타입에 맞는 구성 요소가 하나 이상 있다면 @primary 사용하여 그런 구성 요소 중 하나를 더 중요시 할 수 있다.
@Primary
public class QuickSortAlgorithm implements SortAlgorithm{
	public int[] sort(int[] numbers) {
		//Logic for Quick Sort
		return numbers;
	}
}
