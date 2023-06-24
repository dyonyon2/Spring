package com.dyonyon.spring.basics.springin5steps.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.dyonyon.spring.basics.springin5steps.SpringIn5StepsComponentScanApplication;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

// 2-1. Bean으로 명시
//@Component
// 5-1. default가 singleton이므로 @Scope("singleton") 생략 가능 
// 5-2. Bean Scope : Prototype
// *** 문자열로 넣어도 되지만, ConfigurableBeanFactory을 사용하기!
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Service // **Business Layer라서 @Service! Component도 사용 가능
public class BinarySearchImpl {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());  
	
	/*
	// 4-1. Match되는 Bean이 2개 이상일 때?
	// (1) @primary를 붙인다.
	// (2) Autowired하는 변수 명을 bean 이름과 동일하게 설정한다.
	// (3) @Qualifier("식별자")를 붙인다.
	//  Q1. (1),(2)를 동시에 사용하였을 때?
	//  - primary는 bubble로 놓고 변수 명을 quick로 두었을 때, primary가 우선순위가 높기 때문에 bubble이 match 된다.
	//  Q2. (1),(3)를 동시에 사용하였을 때?
	//  - primary는 bubble로 놓고 qualifier를 quick으로 두었을 때, quick이 match 된다.
	
	// 4-1. (2) Autowired하는 변수 명을 bean 이름과 동일하게 설정한다.
	@Autowired
	private SortAlgorithm bubbleSortAlgorithm;
		 */
	// 4-1. (3) @Qualifier("식별자")를 붙인다.
	@Autowired
	@Qualifier("bubble")
	private SortAlgorithm sortAlgorithm;
	
	/*
	// 2-2. 의존성 부여
	@Autowired
	private SortAlgorithm sortAlgorithm;
	*/
	
	/*
	// 1~3. Constructor(생성자) Injection
	public BinarySearchImpl(SortAlgorithm sortAlgorithm) {
		super();
		this.sortAlgorithm = sortAlgorithm;
	}
	*/
	
	/*
	// 4. Setter Injection 방식. Setter가 없어도 동일하게 동작함. 최근에는 Constructor Injection과 Setter Injection의 차이가 거의 없음.
	public void setSortAlgorithm(SortAlgorithm sortAlgorithm) {
		this.sortAlgorithm = sortAlgorithm;
	}
	*/	
	
	
	// Coupling 개념을 익히기 위한 예제이므로 실제 알고리즘 구현은 PASS!
	// 이진 탐색 흐름 : 배열 정렬 -> 이진 탐색 ->  결과 리턴
	public int binarySearch(int[] numbers, int numberToSearchFor) {
		// 	1-1. 정렬 로직을 여기에 구현
		// 	Q. 배열 정렬하는 방식을 바꾸기 위해서는?
		// 	A. 정렬 방식을 퀵 -> 버블정렬로 바꾸기 위해서는 binarySearch를 변경해주어야한다. BinarySearchImpl 클래스는 버블정렬과 강하게 결합되어 있다.

		/*
		// 	1-2. 정렬 로직을 다른 클래스로 구현
		// 	Q. 배열 정렬하는 방식을 바꾸거나 정렬 로직을 수정하려면?
		// 	A. 버블정렬 알고리즘을 클래스로 분리하여 move out 시켰으므로, 버블 정렬의 로직 수정은 해당 클래스에서 하지 않아도 됨. But 아직 정렬 알고리즘을 선택할 수는 없음.
		BubbleSortAlgorithm bubbleSortAlgorithm = new BubbleSortAlgorithm();
		int[] sortedNumbers2 = bubbleSortAlgorithm.sort(numbers);
		*/
		
		
		// 1-3. Implement를 사용하여 버블정렬과 퀵정렬의 interface를 통해 구현
		// => 수정 및 정렬 알고리즘 선택이 BinarySearchImpl 클래스와 결합도가 매우 낮음.
		int[] sortedNumbers3 = sortAlgorithm.sort(numbers);
		System.out.println(sortAlgorithm);
		
		return 3;
	}
	
	// 5. LifeCycle of Bean
	// 5-1. PostConstruct : Bean이 생성되고 의존성 주입(Autowired로 인한 Dependency Injection)이후 바로 호출된다.
	@PostConstruct
	public void postConstruct() {
		logger.info("IN PostConstruct!");
	}
	
	// 5-2. @PreDestroy : Bean이 제거되기 직전에 호출된다.
	@PreDestroy
	public void preDestroy() {
		logger.info("IN PreDestroy!");
	}

}
