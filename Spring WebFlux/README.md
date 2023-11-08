- Spring WebFlux
  - Build Reactive MicroServices using Spring WebFlux/SpringBoot
  - Abstract
    - Section 1. Why Reactive Programming?
  
  - Section 1. Why Reactive Programming?
    - 프로그래밍의 발전
      - 10~15년 전:
        - Monolith Applications : 여러 Component가 하나의 거대한 Application 내에 있는 구조
        - Depolyed in Application Server : Server 세팅은 많은 노력과 시간이 필요
        - Does not embrace distributed systems : 분산 시스템을 수용하지 못함
      - 현재:
        - Microservices Applications : MSA
        - Deployed in Cloud Environments : Cloud 환경에 배포
        - Embrace Distributed System : 분산 시스템 수용
    - Expectation of the Application : 
      - Response times are expected in milliseconds
      - No Downtime is expected
      - Scale up automatically based on the load
    - Restful API using Spring Boot/MVC
      - ![스크린샷 2023-11-08 094015](https://github.com/jychoi9712/Spring/assets/39684556/b0f63c00-09d7-42a8-a909-90cb499f223c)
    - Spring MVC Limitations
      - ![image](https://github.com/jychoi9712/Spring/assets/39684556/d9f1f126-31bf-455d-ada2-17d3fbba7690)
      - Blocking API 방식이므로 Latency = 모든 처리(DB 저장, API response 등)의 response time의 총합이다.
      - Thread pool size of Embedded tomcat in Spring MVC's is 200
      - Thread pool size를 늘릴순 있지만, 예를들어 10000 concurrent user가 들어오면 Thread pool size를 10000으로 늘릴순 없다. (Thread는 비싼 자원! Heap Memory 1MB를 쉽게 차지함)
      - 성능을 향상시키는 방법은 1,2,3 처리를 병렬처리하는 것
        - Java의 병렬 처리는 Callbacks, Futures이 있음
          - Callbacks
            - Asynchronous methods that accept a callback as a parameter and invokes it when the blocking call completes
            - 단점 : 
              - Writing code with callbacks are hard to compose, and it can quickly lead to complexity in reading and maintaining the code
              - 콜백지옥 (Callbackhell)
            - 장점보다 단점이 더 많음
          - Future
            - Released in Java5
            - Write Asynchronous Code
            - 단점 : 
              - No easy way to combine the result from multiple futures
              - Future.get()으로 결과를 return 받는데 이것이 blocking call임.
          - CompletableFuture
            - Released in Java8
            - Write Asynchronous code in a functional style
            - Easy to compose/combine MultipleFutures
            - 단점 : 
              - Future that returns many elements
                - ex) CompletableFuture< List < Result > > Will need to wait for the whole collection to built and readily available
              - Does not have a great handle for infinite values.
    - Drawbacks of Spring MVC
      - Concurrency is limited when building APIs using spring MVC. (동시성 제한)
      - Blocking code leads to inefficient usage of threads
      - Servlet API at the server level is a blocking one.
      - => Better Option = Reactive Programming

  - Section 2. Introduction to Reactive Programming
    - What is Reactive Programming?
      - A new programming paradigm
      - Asynchronous and non blocking
      - Data flows as an Event/Message driven stream
      - Functional Style Code
      - BackPressure on Data Streams
      - Push-Pull based data flow model
      - ![image](https://github.com/jychoi9712/Spring/assets/39684556/0d676b0d-5835-4267-bfa8-3182c108cf4f)
        - Overwhelm the app with more data. 위 방식으로 요청이 많아지면 App이 많은 data에 압도당함 -> 이를 해결하기 위해 Backpressure 개념 고안됨
        - ![image](https://github.com/jychoi9712/Spring/assets/39684556/db8ad29b-c076-4de2-8a07-409e5a701bdc)
        - Event 2개의 Stream을 요청하고, 데이터를 받고 받은 데이터로 충분히 app에서 처리가 가능하면 데이터를 더 보내지 말라고 cancel() 요청을 보낼 수 있다. 이처럼 app이 data를 controll하는 개념을 Backpressure라고 함
    - When to use Reactive Programming?
      - Use reactive programming when there is a need to build and support apps that can handle higher load with available resources.
    - Reactive App Architecture
      - ![image](https://github.com/jychoi9712/Spring/assets/39684556/66070c21-c6f5-41be-99fb-36ffc7e76f6a)
      - To be non-blocking, basically the request at the server level also needs to be handling the request in a non-blocking fashion.
    - Reactive Streams
      - Reactive Programming의 기반.
      - Specification: 4개의 Interface! (Publisher, Subscriber, Subscription, Processor)
        - Publisher : Method 1개 
          - public interface Publisher< T > {
              public void subscribe(Subscriber<? super T> s);
            }
          - Publiser represents the DataSource (ex. DB, RemoteService)
        - Subscriber : Method 4개 
          - public interface Subscriber< T > {
              public void onSubscribe(Subscription s);
              public void onNext(T t);
              public void onError(Throwable t);
              public onComplete();
            }
          - OnNext() is how the data is sent to the caller from the data source
          - onComplete() is how the data source notifies the app that there is no more data.
        - Subscription : Method 2개. 
          - public interface Subscription {
              public void request(long n);
              public void cancel();
            }
          - Subscription interface is a one which connects the app and the data source.
          - request() : a request call from app requesting for data
          - cancel() : once the app decides that it does not require any more data, then it sends a cancel request to the data source.
        - Processor : 
          - public interface Processor< T, R> extends Subscriber< T >, Publisher< R > {}
          - Processor behaves as a publisher and also as a subscriber.
          - 잘 사용하지는 않음
        - Success Scenario
          - ![image](https://github.com/jychoi9712/Spring/assets/39684556/fda77572-e098-4137-8fef-540b5ec618de)
          - 1. Subscriber initiating the request by invoking the subscribe method of the publisher
          - 2. Publisher handing out the subscription object by invoking the Onsubscribe method of the subscriber.
          - 3. Once a subscriber has the hold of the subscription object, then the subscriber is going to invoke the request function of the subscription object requesting the publisher to send all the data.
          - 4. Once the publisher receives the request to send the data, then it sends the data using the On-x function. That's part of the subscriber interface in the form of events.
          - 5. Once all the data is sent, then the next step is the publisher sending an oncomplete event by invoking the Oncomplete function. That's part of the subscriber.
        - Error/Exception Scenario
          - ![image](https://github.com/jychoi9712/Spring/assets/39684556/5984b7d6-82ef-4039-824a-5d01ef45595a)
          - Error occured because DB is temporarily down or some runtime exception in your code.
          - In this case, the exception will be sent as an event using the Onerror method in the subscriber.
          - Exceptions are treated the same way as like the data in reactive programming, which means in this case, the data or exception will be sent to the app in the form of events.
          - The reactive stream is basically dead when an exception is thrown, which means there is no way data can flow in the same subscription object once an error is thrown.
    - Flow API
      - Released Java9
      - Flow API class holds a contract for reactive streams that lays the foundation for reactive programming. (Contract는 위의 4가지 Interface를 포함. 하지만 ) But no implementation is available as part of JRE
    - What is a nonblocking or reactive RestFul API
      - Non-blocking or reactive API has the behavior of providing end to end Non-blocking communication between the client and the server.
      - Non-blocking or Reactive => Http요청 처리 Thread가 blocking되지 않는 것.
      - 즉 Http 요청 및 Http 응답 처리와 관련된 Thread가 Block 되지 않음을 의미
      - Spring WebFlux is a reactive module which is going to help us in achieving the non-blocking or the reactive behavior.
    - NonBlocking or Reactive API using Spring WebFlux
      - ![image](https://github.com/jychoi9712/Spring/assets/39684556/7052de9b-1d37-450f-b55b-57dcfcda47c3)
      - Netty is a popular runtime that handles the non-blocking IO for you behind the scenes, and as a developer we can just focus on writing endpoints and business logic for your API.
      - Project reactor is a reactive library, which is a implementation of reactive streams which will be used to enable the non-blocking interactions with these external systems.
    - Projcet Reactor(Reactive Library)
      - An implementation of reactive streams specification
      - Spring WebFlux uses Project Reactor by default
      - 참고 url : https://projectreactor.io/docs, https://projectreactor.io/docs/core/release/reference/
      - Reactor is a fourth-generation reactive library, based on the Reactive Streams specification, for building non-blocking applications on the JVM
      - Reactor offers two reactive and composable APIs, Flux [N] and Mono [0|1], which extensively implement Reactive Extensions.
      - Flux & Mono
        - A reactive type that implements the reactive streams specification.
        - A part of reactor-core module
        - Flux : Reactive type to represent 0 to N elements
        - Mono : Reactive type to represent 0 to 1 element
  - Section 3 : Flux, Mono 실습
    - Flux 여러 element, Mono는 0 or 1개 element!
    - Flux, Mono 내의 element에 접근하기 위해서는 subscribing 해야함
      - ex) 
          ```
          public class FluxAndMonoGeneratorService {
              public Flux<String> namesFlux(){
                  return Flux.fromIterable(List.of("dyonyon","sojoong","young")); //db or a
              }

              public Mono<String> nameMono(){
                  return Mono.just("Dyonyon");
              }

              public static void main(String[] args) {
                  FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
                  fluxAndMonoGeneratorService.namesFlux()
                          .subscribe(name -> {
                              System.out.println("Flux Name is : "+name);
                          });

                  fluxAndMonoGeneratorService.nameMono()
                          .subscribe(name -> {
                              System.out.println("Mono Name is : "+name);
                          });
              }
          }
          ```
