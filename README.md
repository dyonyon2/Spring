SPRING 강의

- SPRING이란 : Dependency Injection Framework
  - Dependency Injection?
    - Dependency?

- @Component : 인스턴스화하려는 클래스에 붙여준다.
- @Autowired : 의존성을 가지는 클래스에 붙여준다.

의존성 주입 설명 => https://cheershennah.tistory.com/227
- Beans : 스프링 프레임워크가 관리하는 각 객체
- Autowiring : 
- 의존성 주입(Dependency Injection) : 객체 내부에서 다른 객체를 New로 직접 호출하는 것이 아닌, 외부(스프링)에서 객체를 생성해서 넣어주는 방식으로 결합도를 낮춘다
- Inversion of the control : 
- IOC Container(Inversion Of Controller) 제어의 역전 : 클래스 안에서 다른 클래스를 생성하거나 하는 것으로 의존성이 있었으나, Spring을 통해 인스턴스 생성 및 의존성 주입을 하여 제어를 스프링에게 넘기는 것!
- Application Context : 

- Spring workspace 만드는 간단한 방법 
  - https://start.spring.io/ 만들고 압축해제 후 maven project import 하면 끝

- Spring Projects
  - Spring Boot : 마이크로 서비스 개발하는 프레임워크. Quickly Build Applications.
  - Spring Cloud : Build cloud native apps.
  - Spring Data : Consistent Data Access
  - Spring Integration : Application Integration
  - Spring Batch : Batch Applications
  - Spring Security : Protect your applications
  - Spring HATEOAS : HATEOAS compatible services
  - Others : Spring Session, Web Services, Social, Mobile

- Spring 강점
  - Enables Testable Code : 의존성 주입을 잘햇다면, 단위 테스트가 간편하다
  - No Plumbing Code : 예외처리 같은 코드들을(비지니스로직이 아닌 코드) 생략할 수 있다. (Spring에서 다 처리해줌)
  - Flexible Architecture : Spring은 목적에 따라 모듈로 잘 나뉘어져 있어서 사용하기 좋다. 다른 프레임워크에 같이 사용가능하다.
  - Staying Current : MSA, 클라우드와 같은 최신 기술을 도입함으로 인기를 유지하고 있다.

- eclipse
  - control + space
  - control + 1
  - control + shift + R 메소드, 파일명 등 찾기
  - control + shift + L 
  - control + shift + F 자동 줄 정렬
  - control + shift + O import 정리
  - alt + shift + S 자동 생성 기능(getter, setter 만들기)
  - alt + shift + R 변수, 함수 이름 변경
  - F3 (Goto declaration)
  - F4 (Type Hierarchy)

- Maven
  - POM : project Obeject Model

- CDI (Context Dependency Injection) : JAVA EE 표준에서 의존성 주입하는 인터페이스이다. (스프링에서는 대부분 제공하고 있음)
  - @Inject (@Autowired)
  - @Named (@Component & @Qualifier)
  - @Singleton (Scope)

- Bean 방식
  - JAVA 방식 
    - CDI (JAVA EE 표준 방식) ex. @Inject, @Named
    - SPRING 방식 ex. @Component, @Qualifier
  - XML 방식 ex. applicationContext.xml -> Bean 생성, context component-scan 설정 등

- Application Conext = Bean Factory++
- Bean Factory 

- Componenet Annotations
  - @Component - Generic Component. 모든 Layer에서 사용
  - @Repository - Encapsulating storage, retrieval, and search behavior typically from a relational database. Data Layer에서 사용
  - @Service - Business Service Facade. Business Layer에서 사용
  - @Controller - Controller in MVC pattern. Web Layer에서 사용

- JunitTest
  - Java에서 제공하는 일반적인 단위 테스트임.
  - 단위(모듈) 테스트에서 많이 사용됨
- mockito
  - 마케팅 테스트.
  - Layer 별로 따로 테스트를 할 수 있음
  - Spring 전체를 불러올 필요없이, Mockito만으로 단위 테스트가 가능하다.
  - Stub : canned answer(미리 준비된 답변)를 호출한 쪽에 제공(provide) 한다.
    - 비지니스 레이어의 코드를 데이터 레이어의 get Data는 interface로 만들고, test 코드에서 interface를 implement하여 stub 클래스를 만들어 데이터 레이어를 통해 값을 가져온 것처럼 값을 리턴하여 해당 레이어만 테스트할 수 있도록 함
    - But 여러 시나리오를 테스트하려면 새로운 클래스를 계속 만들어줘야한다는 단점이 있다. @Test는 당연히 만들어야하지만 그 안에 값이 다른 stub1, stub2,,,를 계속 만들어주고 불러야함 (Stub 클래스를 계속 만들어 주어야함)
  - Mock
    - 실제의 모듈을 "흉내"내는 "가짜" 모듈을 작성하여 테스트의 효용성을 높이는데 사용하는 객체이다.
    - Write하기 간단하며, 유지보수가 쉽다. (Stub와달리 바)

- Spring Boot 이전... setting up Spring Projects was NOT easy! A lot of things to configure
  - Dependency Management(pom.xml)
  - Define Web App(web.xml)
  - Manage Spring Beans(context.xml)
  - Implement Non Functional Requirements (NFRs : Logging, Error Handling, Monitoring,...)

- Spring Boot 시작하기
  - https://start.spring.io/ 에서 설정한 뒤 시작 (dependencies에서 Spring Web 추가! => MVC, REST API, Web Application Progarm)
  - REST API 호출을 처리할 클래스에 @RestController annotation 추가
  - 처리할 Function에 @RequestMapping("URL") 추가
  => 간단한 REST API 핸들러 완성

- Spring Boot's Goal
  - Build Quickly 
    - Spring Initializer
    - Spring Boot Starter Projects (빠른 종속성 정의)
    - Spring Boot Auto Configuration 
    - Spring Boot DevTools
  - Production-Ready
    - Logging
    - Different configuration for different environments (Profiles, ConfigurationProperies)
    - Monitoring (Spring Boot Actuator)

