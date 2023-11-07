package com.dyonyon.spring.basics.springin5steps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.dyonyon.spring.basics.springin5steps.basic.BinarySearchImpl2;

@SpringBootApplication
public class SpringIn5StepsBasicApplication2 {
	
	// 2-1. Beans란 무엇인가? -> 객체가 인스턴스된 것. 이 예제에서는 binarySearchImpl, BubbleSortAlgorithm
	// 2-2. Bean의 의존성들이 무엇인가? -> SortAlgorithm이 BinarySearchImpl의 의존성이다
	// 2-3. Beans을 어디서 찾아야 할까? => @SpringBootApplication이 해당 패키지 내의 하위 패키지들을 스캔하여 관리한다.
	// 2-4. Beans를 어떻게 가져와서 사용하나? => 모든 Beans를 관리하는 Application Context에서 가져온다. 
	
	public static void main(String[] args) {
		
		/*
		// 1. 의존성과 낮은 결합도만들기 
		// 1-1~1-2. 정렬 알고리즘은 BinarySearchImpl에 종속성이고, BinarySearchImpl은 정렬 알고리즘에 의존한다.
		// 1-3.  정렬 알고리즘을 분리된 종속성으로 만들어, 정렬 알고리즘의 인스턴스를 만들어서 전달해주는 방식이다.
		BinarySearchImpl binarySearchImpl = new BinarySearchImpl(new BubbleSortAlgorithm());
		int result = binarySearchImpl.binarySearch(new int[] {12,4,6}, 3);
		System.out.println(result);
		
		BinarySearchImpl binarySearchImpl2 = new BinarySearchImpl(new QuickSortAlgorithm());
		int result2 = binarySearchImpl.binarySearch(new int[] {12,4,6}, 3);
		System.out.println(result2);
		
		SpringApplication.run(SpringIn5StepsApplication.class, args);
		*/
		
		/*
		// 2-4. SpringApplication.run은 Application Context를 반환한다.
		// BubbleSortAlgorithm만 Component 붙였을때!
		ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringIn5StepsApplication.class, args);
		BinarySearchImpl binarySearchImpl = applicationContext.getBean(BinarySearchImpl.class);
		int result = binarySearchImpl.binarySearch(new int[] {12,4,6}, 3);
		System.out.println(result);
		*/
		
		// 3-1. QuickSortAlgorithm도 Component를 붙이면?
		//   => 에러가 발생한다. Parameter 0 of constructor in com.dyonyon.spring.basics.springin5steps.BinarySearchImpl required a single bean, but 2 were found: bubbleSortAlgorithm, quickSortAlgorithm 
		// 3-2. 특정 타입에 맞는 구성 요소가 하나 이상 있다면 @primary 사용하여 그런 구성 요소 중 하나를 더 중요시 할 수 있다.
		// 3-3. SortAlgorithm 둘다 component를 지우면 wiring에서 에러 발생. Parameter 0 of constructor in com.dyonyon.spring.basics.springin5steps.BinarySearchImpl required a bean of type 'com.dyonyon.spring.basics.springin5steps.SortAlgorithm' that could not be found. 
		// 3-4. 모든 component를 지우면 getBean에서 에러 발생. Exception in thread "main" org.springframework.beans.factory.NoSuchBeanDefinitionException
		
		
		// 5. Bean Scope (singleton, prototype, request, session)
		//  - @Scope("singleton")
		//  - applicationContext에게 특정 클래스의 인스턴스(Bean)을 요청하였을 때 어떻게 되나?
		// 5-1. singleton (default) : One Instance Per Sprint Context    
		// 5-2. prototype : New Bean Whenever Requested
		// 5-3. request : One Bean Per HTTP Request
		// 5-4. session : One Bean Per HTTP Session
		ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringIn5StepsBasicApplication2.class, args);
		BinarySearchImpl2 binarySearchImpl = applicationContext.getBean(BinarySearchImpl2.class);
		System.out.println(binarySearchImpl);
		
		BinarySearchImpl2 binarySearchImpl2 = applicationContext.getBean(BinarySearchImpl2.class);
		System.out.println(binarySearchImpl2);
		
		
		int result = binarySearchImpl.binarySearch(new int[] {12,4,6}, 3);
		System.out.println(result);
	}

}
