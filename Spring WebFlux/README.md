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
          - 데이터를 생산하는 생산자.
          - 생산된 데이터를 소비할 소비자가(subscriber)가 등록(subscribe)될 때까지 아무일도 일어나지 않음
          - ```
            public interface Publisher< T > {
              public void subscribe(Subscriber<? super T> s);
            }
            ```
          - Publisher represents the DataSource (ex. DB, RemoteService)
        - Subscriber : Method 4개 
          - 데이터를 소비하는 소비자.
          - ```
            public interface Subscriber< T > {
              public void onSubscribe(Subscription s);
              public void onNext(T t);
              public void onError(Throwable t);
              public onComplete();
            }
            ```
          - OnNext() is how the data is sent to the caller from the data source
          - onComplete() is how the data source notifies the app that there is no more data.
        - Subscription : Method 2개. 
          - ```
            public interface Subscription {
              public void request(long n);
              public void cancel();
            }
            ```
          - Subscription interface is a one which connects the app and the data source.
          - request() : a request call from app requesting for data
          - cancel() : once the app decides that it does not require any more data, then it sends a cancel request to the data source.
        - Processor : 
          - ```
              public interface Processor<T,R> extends Subscriber<T>, Publisher<R> {

              }
            ```
          - Processor behaves as a publisher and also as a subscriber.
          - 잘 사용하지는 않음
        - Ex) publisher, subscriber 구분 예제
          - ```
            Flux<Integer> flux = Flux.range(1, 10);
            flux.subscribe(number -> System.out.println(number));
            ```
          - publisher = 생산자 = Flux 클래스
          - operator = 연산자 = range
          - subscriber = 데이터를 소비하는 소비자 = System.out.println
        -  ![image](https://github.com/jychoi9712/Spring/assets/39684556/0963d747-a426-4580-be38-2204b479af6e)
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
                  return Flux.fromIterable(List.of("dyonyon","sojoong","young")); 
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
      - Flux.fromIterable 뒤에 .log()를 추가하면 subscriber-publisher 간의 모든 events가 출력
        - ```
        16:55:20.675 [main] INFO reactor.Flux.Iterable.1 - | onSubscribe([Synchronous Fuseable] FluxIterable.IterableSubscription)
        16:55:20.678 [main] INFO reactor.Flux.Iterable.1 - | request(unbounded)
        16:55:20.678 [main] INFO reactor.Flux.Iterable.1 - | onNext(dyonyon)
        Flux Name is : dyonyon
        16:55:20.691 [main] INFO reactor.Flux.Iterable.1 - | onNext(sojoong)
        Flux Name is : sojoong
        16:55:20.691 [main] INFO reactor.Flux.Iterable.1 - | onNext(young)
        Flux Name is : young
        16:55:20.692 [main] INFO reactor.Flux.Iterable.1 - | onComplete()
        ```
          - requst(unbound)는 default 값으로 모든 데이터를 달라고 요청하는 것
      - Flux, Mono를 사용한 Reactive code의 Test는 StepVerifier를 사용하여 Unit Test 진행
        - ex) Flux Unit Test 예제
          ```
          class FluxAndMonoGeneratorServiceTest {

            FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

            @Test
            void namesFlux() {

                //given

                //when
                var stringFlux = fluxAndMonoGeneratorService.namesFlux();

                //then
                StepVerifier.create(stringFlux)
                //.expectNext("dyonyon","sojoong","young")
                //.expectNextCount(3)  
                //위에서 Next로 데이터를 사용했기 때문에 남은 데이터 개수는 0이 된다.
                .expectNext("dyonyon")
                .expectNextCount(2)
                .verifyComplete();
            }
          }
          ```
          - StepVerifier.Create()은 subscribe를 자동으로 호출해준다.
      - Transforming Data Using Operators in Project Reactor
        - map() 연산자
          - element의 form을 변환함. Java Streams API의 map()과 유사함
          - ex) map() 연산자 예제
          ```
            public Flux<String> namesFlux_map(){
                return Flux.fromIterable(List.of("dyonyon","sojoong","young"))
                        .map(String::toUpperCase)     // 방법 1. 함수형 인터페이스
                        //.map(s -> s.toUpperCase())  // 방법 2. 람다식
                        .log();
            }
          ```
          - Reactive Streams 은 멱등(Immutable)이다.
            - ex) Immutable 예제
            ```
                  public Flux<String> namesFlux_immutability(){
                    var nameFlux = Flux.fromIterable(List.of("dyonyon","sojoong","young"));

                    nameFlux.map(String::toUpperCase);

                    return nameFlux;
                  }
                  // 출력 "dyonyon","sojoong","young"
            ```
            ```
                // Flux.java map함수 구현체
                // 전달 받은 Flux를 변경하는 것이 아닌 new한 Flux를 return.
                // 즉 map함수를 호출한 Flux는 변경되지 않음
                public final <V> Flux<V> map(Function<? super T, ? extends V> mapper) {
                  if (this instanceof Fuseable) {
                    return onAssembly(new FluxMapFuseable<>(this, mapper));
                  }
                  return onAssembly(new FluxMap<>(this, mapper));
                }
            ```
        - filter() 연산자
          - element를 filter하는 기능. Java Streams API의 filter()와 유사함
          - ex) filter() 연산자 예제
          ```
            public Flux<String> namesFlux_filter(int stringLength){
                return Flux.fromIterable(List.of("dyonyon","sojoong","young"))
                        .filter(s->s.length()>stringLength)
                        .log();
            }

            // 출력 "dyonyon","sojoong"
          ```
          - ex) filter(), map() 연산자 예제
          ```
            public Flux<String> namesFlux_filter_map(int stringLength){
                return Flux.fromIterable(List.of("dyonyon","sojoong","young"))
                        .filter(s->s.length()>stringLength)
                        .map(s->s.length()+"-"+s)
                        .log();
            }

            // 출력 "7-dyonyon", "7-sojoong"
          ```
        - flatMap() 연산자
          - Transforms one source element to Flux of 1 to N elements
            - ex) "ALEX" -> Flux.just("A","L","E","X")
          - Reactive Type(Flux or Mono) 를 반환하는 Transformation일 때 사용
          - ex) flatMap() 연산자 예제
          ```
              public Flux<String> namesFlux_flatMap(int stringLength){
                  return Flux.fromIterable(List.of("dyonyon","sojoong","young"))
                          .filter(s->s.length()>stringLength)
                          //.flatMap(s->splitString(s)) // 람다식
                          .flatMap(this::splitString)   // 함수형 인터페이스
                          .log();
              }

              //dyonyon -> Flux(d,y,o,n,y,o,n)
              public Flux<String> splitString(String name){
                  var charArray = name.split("");
                  return Flux.fromArray(charArray);
              }

              // 출력 "d", "y", "o", "n", "y", "o", "n", "s", "o", "j", "o", "o", "n", "g"
          ```
          - ex) 비동기 async flatMap()
          ```
            // Service.java
            public Flux<String> namesFlux_flatMap_async(int stringLength){
                return Flux.fromIterable(List.of("dyonyon","sojoong","young"))
                        .filter(s->s.length()>stringLength)
                        //.flatMap(s->splitString_withDelay(s))
                        .flatMap(this::splitString_withDelay)
                        .log();
            }
            
            /// Test.java
            @Test
            void namesFlux_flatmap_async() {

                //given
                int stringLength = 5;

                //when
                var namesFlux = fluxAndMonoGeneratorService.namesFlux_flatMap_async(stringLength).log();

                //then
                StepVerifier.create(namesFlux)
                        .expectNext("d", "y", "o", "n", "y", "o", "n", "s", "o", "j", "o", "o", "n", "g")
                        .verifyComplete();

            }

            // 이 경우 random value에 따라 dyonyon이 먼저 리턴될 수도, sojoong이 먼저 리턴될 수도, 섞여서 리턴될 수도 있음!
          ```
            - *** flatMap의 비동기적 특성이며, 순서가 중요한 경우에는 flatMap을 사용할 수 없다. ***
          - flatMap() in Mono
            - 항상 Mono< T >를 반환함. Mono는 여러 개를 반환할 수 없으니 Mono List 하나롤 만들어 반환
            - REST API call 혹은 비동기 기능 혹은 Mono를 return할 때 사용함
            ```
              public Mono<List<String>> namesMono_flatMap(int stringLength){
                  return Mono.just("dyonyon")
                          .map(String::toUpperCase)
                          .filter(s->s.length()>stringLength)
                          .flatMap(this::splitStringMono);
                          //Mono<List of D,Y,O,N,Y,O,N
              }
              ...
              public Mono<List<String>> splitStringMono(String name){
                  var charArray = name.split("");
                  var charList = List.of(charArray); // array -> list
                  return Mono.just(charList);
              }

              //출력 : onNext([D, Y, O, N, Y, O, N])
            ```
          - flatMapMany() in Mono
            - flatMap()과 동일, but Mono를 Flux로 변환하여 반환!
            - ex) flatMapMany() 연산자 예제
              ```
                public Flux<String> namesMono_flatMapMany(int stringLength){
                    return Mono.just("dyonyon")
                            .map(String::toUpperCase)
                            .filter(s->s.length()>stringLength)
                            .flatMapMany(this::splitString);
                }

                public Flux<String> splitString(String name){
                    var charArray = name.split("");
                    return Flux.fromArray(charArray);
                }
              ```
        - map() vs flatmap()
          - map() : 
            - 1 대 1 변형, T to V의 간단한 변형
            - 간단한 동기(Synchronous) 변형
            - Publisher(Reactive Types)를 반환하는 변형을 지원하지 않음
          - flatmap() :
            - 1 대 N 변형, 변형만 하는것이 아니라, Flux 혹은 Mono를 subscribes한 뒤 이를 평면화하여 다운스트림 operator에게 보냄
            - 비동기(Asynchronous) 변형
            - Flux 혹은 Mono를 반환하는 변형.
        - concatMap() 연산자
          - flatMap() 연산과 비슷, But Reactive Streams의 순서를 유지함!
          - ex) concatMap() 연산자 예제
          ```
            public Flux<String> namesFlux_concatMap(int stringLength){
                return Flux.fromIterable(List.of("dyonyon","sojoong","young"))
                        .filter(s->s.length()>stringLength)
                        //.flatMap(s->splitString_withDelay(s))
                        .concatMap(this::splitString_withDelay)
                        .log();
            }

            // 출력 고정 dyonyon -> sojoong
            // 순서가 먼저인 dyonyon이 순서가 보장되기 때문에 flatMap() 보다 소요시간이 길다.
          ```
        - transform() 연산자
          - 타입을 다른 타입으로 변형할 때 사용
          - 함수형 인터페이스 함수만 accept
          - Function 을 추출하여 변수에 할당한 뒤 transform()을 사용하여 재사용가능
          - Input&Output : Publisher(Flux or Mono)
          - ex) transform() 연산자 예제
          ```
              public Flux<String> namesFlux_transform(int stringLength){
                  Function<Flux<String>, Flux<String>> filterMap = name -> name.map(String::toUpperCase).filter(s->s.length()>stringLength);

                  return Flux.fromIterable(List.of("dyonyon","sojoong","young"))
                          .transform(filterMap)
                          .flatMap(this::splitString)
                          .log();
              }
          ```
        - defaultIfEmpty() & switchIfEmpty() 연산자
          - DataSource에서 항상 data를 return해주지는 않음. onNext()가 없이 바로 onComplete()되는 경우.
          - defaultIfEmpty()는 값이 없다면 String 인자를 return
          - switchIfEmpty()는 값이 없다면 Flux 인자를 return
          - 위 2가지 연산자를 통해서 default value를 operator에게 provide함
          - ex) defaultIfEmpty() & switchIfEmpty() 연산자 예제
          ```
              public Flux<String> namesFlux_transform_default(int stringLength){
                  Function<Flux<String>, Flux<String>> filterMap = name -> name.map(String::toUpperCase).filter(s->s.length()>stringLength).flatMap(this::splitString);

                  return Flux.fromIterable(List.of("dyonyon","sojoong","young"))
                          .transform(filterMap)
                          .defaultIfEmpty("default")
                          .log();
              }

              public Flux<String> namesFlux_transform_switch(int stringLength){
                  Function<Flux<String>, Flux<String>> filterMap = name -> name.map(String::toUpperCase).filter(s->s.length()>stringLength).flatMap(this::splitString);

                  var defaultFlux = Flux.just("default!").transform(filterMap);

                  return Flux.fromIterable(List.of("dyonyon","sojoong","young"))
                          .transform(filterMap)
                          .switchIfEmpty(defaultFlux)
                          .log();
              }
          ```
      - Combining Flux & Mono 연산자
        - concat() & concatWith()
          - Reactive Stream 2개를 1개로 결합하는데 사용
          - concatenation of Reactive Stream은 순서대로 발생
            - 처음 것이 subscribed되고 끝나야 두번째꺼 subscribed되고 끝남
          - concat() : static method id Flux
          - concatWith() : instance method in Flux and Mono
          - ex) concat(), concatWith() 연산자 예제
          ```
              public Flux<String> explore_concat(){
                  var abcFlux = Flux.just("A","B","C");
                  var defFlux = Flux.just("D","E","F");

                  return Flux.concat(abcFlux,defFlux).log();
              }

              public Flux<String> explore_concatWith(){
                  var abcFlux = Flux.just("A","B","C");
                  var defFlux = Flux.just("D","E","F");

                  return abcFlux.concatWith(defFlux);
              }
              public Flux<String> explore_concatWith_Mono(){
                  var aMono = Mono.just("A");
                  var bMono = Mono.just("B");

                  return aMono.concatWith(bMono);
              }
          ```
        - merge() & mergeWith()
          - Combine two publishers in to one
          - 2개의 publisher가 동시에 subscribe하는 특징
          - concat()은 순서대로 subscribe함을 기억!
          - merge() : static method in Flux
          - mergeWith() : instance method in Flux and Mono
          - ex) merge(), mergeWith() 연산자 예제
          ```
            public Flux<String> explore_merge(){
                var abcFlux = Flux.just("A","B","C").delayElements(Duration.ofMillis(100)); //A,B
                var defFlux = Flux.just("D","E","F").delayElements(Duration.ofMillis(125)); //D,E

                return Flux.merge(abcFlux,defFlux).log();
            }

            // 출력 : A D B E C F

            public Flux<String> explore_mergeWith(){
                var abcFlux = Flux.just("A","B","C").delayElements(Duration.ofMillis(100)); 
                var defFlux = Flux.just("D","E","F").delayElements(Duration.ofMillis(125)); 

                return abcFlux.mergeWith(defFlux).log();
            }

            // 출력 : A D B E C F

            public Flux<String> explore_mergeWith_mono(){
                var aMono = Mono.just("A"); //A
                var bMono = Mono.just("B"); //B

                return aMono.mergeWith(bMono).log();
            }

            // 출력 : A B
          ```
        - mergeSequential()
          - Combine Two publishers (Flux) in to one
          - 2개의 publisher가 동시에 subscribe하지만, merge는 순차적으로 진행
          - ex) mergeSequential() 예제
          ```
            public Flux<String> explore_mergeSequential(){
                var abcFlux = Flux.just("A","B","C")
                        .delayElements(Duration.ofMillis(100)); //A,B
                var defFlux = Flux.just("D","E","F")
                        .delayElements(Duration.ofMillis(125)); //D,E

                return Flux.mergeSequential(abcFlux,defFlux).log();
            }
          ```
        - zip() & zipWith()
          - 여러 Publisher를 하나도 압축할 때 사용
          - zip() 
            - Static method that's part of the Flux
            - 2~8개의 publisher를 하나로 merge 할 때 사용
          - zipWith()
            - Instance method that's part of the Flux and Mono
            - 2개의 publisher를 하나로 merge할 때 사용.
            - Waits for all the publishers involved in the transformation to emit one element
              - It continues until one of the publisher involved sends an Oncomplete event.
          - ex) zip(), zipWith() 예제
          ```
              public Flux<String> explore_zip(){
                  var abcFlux = Flux.just("A","B","C");
                  var defFlux = Flux.just("D","E","F");

                  return Flux.zip(abcFlux,defFlux,(first, second) -> first+second).log();  // AD, BE, CF
              }

              public Flux<String> explore_zip_1(){
                  var abcFlux = Flux.just("A","B","C");
                  var defFlux = Flux.just("D","E","F");
                  var _123Flux = Flux.just("1","2","3");
                  var _456Flux = Flux.just("4","5","6");

                  return Flux.zip(abcFlux,defFlux,_123Flux,_456Flux).map(t4 -> t4.getT1()+t4.getT2()+t4.getT3()+t4.getT4()).log();  // AD, BE, CF
              }

              public Flux<String> explore_zipWith(){
                  var abcFlux = Flux.just("A","B","C");
                  var defFlux = Flux.just("D","E","F");

                  return abcFlux.zipWith(defFlux,(first, second) -> first+second).log();  // AD, BE, CF
              }

              public Mono<String> explore_zipWith_Mono(){
                  var aMono = Mono.just("A");
                  var bMono = Mono.just("D");

                  return aMono.zipWith(bMono).map(t2->t2.getT1()+t2.getT2()).log();  // AB
              }
          ```
  - Section 4 : Spring WebFlux
    - document : https://docs.spring.io/spring-framework/docs/5.0.0.RELEASE/spring-framework-reference/web-reactive.html
      - 2가지 Type : Annotated Controllers & Functional EndPoints
    - Project
      - 강의에서 만들 MicroServices의 구조
        - ![image](https://github.com/dyonyon2/Spring/assets/39684556/4ee5ff70-8477-4545-9a18-f5e6013f0c84)
      - Settings
        - 강의 프로젝트의 구조가 멀티 프로젝트 구조이므로 build.gradle을 수정해주지 않으면 오류 발생! 블로그에 에러 정리해 놓음
          - https://blog.naver.com/dyonyon2/223263664037
        - MongoDB가 설치되어있지 않으면 에러 발생할 것임. MongoDB 설치도 블로그에 정리해 놓음
          - https://blog.naver.com/dyonyon2/223265695024
        - MongoDB 실행 : mongod
        - MongoDB 쉘 연결 : mongosh
      - 간단한 Non Blocking API Flux & Mono
        - ex) localhost:8080/flux 접속시 API return 확인
          ```
          @RestController
          public class FluxMonoController {


              @GetMapping("/flux")
              public Flux<Integer> flux(){
                  return Flux.just(1,2,3)
                          .log();
              }

              @GetMapping("/mono")
              public Mono<String> helloWorldMono(){
                  return Mono.just("hello-world");
              }
          }
          ```
      - Streaming Endpoint Using Spring WebFlux
        - Streaming Endpoint란 새로운 데이터가 도착할 때 클라이언트에게 지속적으로 업데이트를 보내는 엔드포인트
        - Server Sent Events(SSE)와 비슷한 개
        - ex) 1초마다 OnNext로 1 증가한 값을 계속해서 보내줌
          ```
            @RestController
            public class FluxMonoController {
              ...
              @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
              public Flux<Long> stream(){
                  return Flux.interval(Duration.ofSeconds(1))
                          .log();
            }
          ```
      - Automated Testing Using JUnit5
        - Automated Test는 좋은 소프트웨어 제공을 위해서는 꼭 필요함
        - Integration Test와 Unit Test를 다룰 것임
        - Integration Test
          - Integration Tests는 Application의 end to end의 테스트
          - ![image](https://github.com/dyonyon2/Spring/assets/39684556/4361f891-c465-429e-89c2-f0cf66d74762)
        - Unit Test
          - Unit test는 관심 있는 클래스와 메서드만 테스트
          - ![image](https://github.com/dyonyon2/Spring/assets/39684556/24306624-6c25-4b0d-93a6-407961bf2b14)
          - ex) Flux 방식 3개, Mono, Stream
            ```
            @WebFluxTest(controllers = FluxMonoController.class)
            public class FluxMonoControllerUnitTest {

                @Autowired
                WebTestClient webTestClient;

                @MockBean
                private MoviesInfoService moviesInfoServiceMock;

                @Test
                void flux() {

                    webTestClient
                            .get()
                            .uri("/flux")
                            .exchange()
                            .expectStatus()
                            .is2xxSuccessful()
                            .expectBodyList(Integer.class)
                            .hasSize(3);
                }

                @Test
                void flux_approach2() {

                    var flux = webTestClient
                            .get()
                            .uri("/flux")
                            .exchange()
                            .expectStatus()
                            .is2xxSuccessful()
                            .returnResult(Integer.class)
                            .getResponseBody();

                    StepVerifier.create(flux)
                            .expectNext(1, 2, 3)
                            .expectComplete();
                }

                @Test
                void flux_approach3() {

                    webTestClient
                            .get()
                            .uri("/flux")
                            .exchange()
                            .expectStatus()
                            .is2xxSuccessful()
                            .expectBodyList(Integer.class)
                            .consumeWith(listEntityExchangeResult -> {
                                var responseBody = listEntityExchangeResult.getResponseBody();
                                assert (responseBody != null ? responseBody.size() : 0) ==3;
                            });


                }


                /**
                 * Copied from approach 2
                 */
                @Test
                void stream() {

                    var flux = webTestClient
                            .get()
                            .uri("/stream")
                            .exchange()
                            .expectStatus()
                            .is2xxSuccessful()
                            .returnResult(Integer.class)
                            .getResponseBody();

                    StepVerifier.create(flux)
                            .expectNext(0, 1, 2)
                            .thenCancel()
                            .verify();
                }


                @Test
                void mono() {

                     webTestClient
                            .get()
                            .uri("/mono")
                            .exchange()
                            .expectStatus()
                            .is2xxSuccessful()
                            .expectBody(String.class)
                            .consumeWith(stringEntityExchangeResult ->{
                                var response = stringEntityExchangeResult.getResponseBody();
                                assertEquals("hello-world", response);
                            });

                }
            }
            ```
      - 데이터 만들기 MovieInfo 
        - ex) domain/MovieInfo
          ```
            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            @Document
            @Validated
            public class MovieInfo {
                @Id
                private String movieInfoId;
                @NotBlank(message = "movieInfo.name must be present")
                private String name;
                @NotNull
                @Positive(message = "movieInfo.year must be a Positive Value")
                private Integer year;

                @NotNull
                private List<@NotBlank(message = "movieInfo.cast must be present") String> cast;
                private LocalDate release_date;
            }
          ```
        - MongoDB 연결
          - ReactiveMongoRepository< Table명 , id자료형 >를 상속하여 연결
          - ex) repository/MovieInfoRepository
            ```
              public interface MovieInfoRepository extends ReactiveMongoRepository<MovieInfo, String> {

                  Flux<MovieInfo> findByYear(Integer year);

                  Mono<MovieInfo> findByName(String name);
              }
            ```
          - MongoDB 설정
            - ex) application.yml
              ```
                spring:
                  profiles:
                    active: local
                ---
                spring:
                  config:
                    activate:
                      on-profile: local
                  data:
                    mongodb:
                      host: localhost
                      port: 27017
                      database: local
              ```
          - MongoDB Test
            - @DataMongoTest annotation을 사용하면 embedded MongoDB가 사용됨
            - @ActiveProfiles("test") annotation을 사용하여 application.yml에 설정한 DB 주소를 안바라보도록 변경
            - findall()과 같은 기본적인 sql 기능들이 ReactiveMongoRepository에 있음
            - 비동기 방식이므로 그 뒤에 다른 작업에서 결과를 사용해야할 때는 block(), blockLast()를 사용하여 동기처럼 진행
              - But Reactive Project에서는 Harmful! 되도록 테스트용으로만 사용
            - ex) findAll, findById, findByYear, save, update, delete
              ```
                @DataMongoTest
                @ActiveProfiles("test")
                public class MoviesInfoRepositoryIntgTest {

                    @Autowired
                    MovieInfoRepository movieInfoRepository;

                    @BeforeEach
                    void setUp() {

                        var movieinfos = List.of(new MovieInfo(null, "Batman Begins",
                                        2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15")),
                                new MovieInfo(null, "The Dark Knight",
                                        2008, List.of("Christian Bale", "HeathLedger"), LocalDate.parse("2008-07-18")),
                                new MovieInfo("abc", "Dark Knight Rises",
                                        2012, List.of("Christian Bale", "Tom Hardy"), LocalDate.parse("2012-07-20")));

                        movieInfoRepository.saveAll(movieinfos)
                                .blockLast();
                    }

                    @AfterEach
                    void tearDown() {
                        movieInfoRepository.deleteAll().block();
                    }

                    @Test
                    void findAll() {

                        var moviesFlux = movieInfoRepository.findAll().log();

                        StepVerifier.create(moviesFlux)
                                .expectNextCount(3)
                                .verifyComplete();

                    }

                    @Test
                    void findById() {

                        var movieInfo = movieInfoRepository.findById("abc");

                        StepVerifier.create(movieInfo)
                                .assertNext(movieInfo1 -> {
                                    assertEquals("Dark Knight Rises", movieInfo1.getName());
                                });
                    }

                    @Test
                    void saveMovieInfo() {

                        var movieInfo = new MovieInfo(null, "Batman Begins1",
                                2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15"));

                        var savedMovieInfo = movieInfoRepository.save(movieInfo);

                        StepVerifier.create(savedMovieInfo)
                                .assertNext(movieInfo1 -> {
                                    assertNotNull(movieInfo1.getMovieInfoId());
                                });

                    }

                    @Test
                    void updateMovieInfo() {

                        var movieInfo = movieInfoRepository.findById("abc").block();
                        movieInfo.setYear(2021);

                        var savedMovieInfo = movieInfoRepository.save(movieInfo);

                        StepVerifier.create(savedMovieInfo)
                                .assertNext(movieInfo1 -> {
                                    assertNotNull(movieInfo1.getMovieInfoId());
                                    assertEquals(2021, movieInfo1.getYear());
                                });

                    }

                    @Test
                    void deleteMovieInfo() {

                        movieInfoRepository.deleteById("abc").block();

                        var movieInfos = movieInfoRepository.findAll();

                        StepVerifier.create(movieInfos)
                                .expectNextCount(2)
                                .verifyComplete();

                    }

                    @Test
                    void findMovieInfoByYear() {

                        var movieInfosFlux = movieInfoRepository.findByYear(2005).log();

                        StepVerifier.create(movieInfosFlux)
                                .expectNextCount(1)
                                .verifyComplete();



                    }

                    @Test
                    void findByName() {

                        var movieInfosMono = movieInfoRepository.findByName("Batman Begins").log();

                        StepVerifier.create(movieInfosMono)
                                .expectNextCount(1)
                                .verifyComplete();


                    }
                }

              ```
      - MovieInfo Controller와 Service 만들기
        - @RestController가 붙은 controller에서 REST API를 받아 @PostMapping에서 받아서 처리하는 Service 호출. Service에서는 Repository를 불러와 DB에 add
        - ex) MoviesInfoController
          ```
            @RestController
            @RequestMapping("/v1")
            @Slf4j
            public class MoviesInfoController {

                @Autowired
                private  MoviesInfoService moviesInfoService;

            //    public MoviesInfoController(MoviesInfoService moviesInfoService){
            //        this.moviesInfoService = moviesInfoService;
            //    }

                @PostMapping("/movieinfos")
                @ResponseStatus(HttpStatus.CREATED)
                public Mono<MovieInfo> addMovieInfo(@RequestBody MovieInfo movieInfo){
                    return moviesInfoService.addMovieInfo(movieInfo);
                }

                @GetMapping("/movieinfos/{id}")
                public Mono<MovieInfo> getMovieInfoById(@PathVariable String id){
                    return moviesInfoService.getMovieInfoById(id);
                }

            }
          ```
        - ex) MovieInfoService
          ```
            @Service
            @Slf4j
            public class MoviesInfoService {


                private MovieInfoRepository movieInfoRepository;

                public MoviesInfoService(MovieInfoRepository movieInfoRepository) {
                    this.movieInfoRepository = movieInfoRepository;
                }

                public Mono<MovieInfo> addMovieInfo(MovieInfo movieInfo) {
                    log.info("addMovieInfo : {} " , movieInfo );
                    return movieInfoRepository.save(movieInfo)
                            .log();
                }

                public Mono<MovieInfo> getMovieInfoById(String id) {
                    return movieInfoRepository.findById(id);
                }
            }

          ```
        - ex) 위 addMovieInfo Integration Test
          ```
            @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
            @ActiveProfiles("test")
            @AutoConfigureWebTestClient
            public class MovieInfoControllerIntgTest {

                @Autowired
                private WebTestClient webTestClient;

                @Autowired
                private MovieInfoRepository movieInfoRepository;

                static String MOVIES_INFO_URL = "/v1/movieinfos";

                @BeforeEach
                void setUp() {
                    var movieinfos = List.of(new MovieInfo(null, "Batman Begins",
                                    2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15")),
                            new MovieInfo(null, "The Dark Knight",
                                    2008, List.of("Christian Bale", "HeathLedger"), LocalDate.parse("2008-07-18")),
                            new MovieInfo("abc", "Dark Knight Rises",
                                    2012, List.of("Christian Bale", "Tom Hardy"), LocalDate.parse("2012-07-20")));

                    movieInfoRepository
                            .deleteAll()
                            .thenMany(movieInfoRepository.saveAll(movieinfos))
                            .blockLast();
                }
                @Test
                void addNewMovieInfo() {

                    var movieInfo = new MovieInfo(null, "Batman Begins",
                            2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15"));
                    webTestClient
                            .post()
                            .uri(MOVIES_INFO_URL)
                            .bodyValue(movieInfo)
                            .exchange()
                            .expectStatus()
                            .isCreated()
                            .expectBody(MovieInfo.class)
                            .consumeWith(movieInfoEntityExchangeResult -> {
                                var savedMovieInfo = movieInfoEntityExchangeResult.getResponseBody();
                                assert Objects.requireNonNull(savedMovieInfo).getMovieInfoId() != null;

                            });
                }

                @Test
                void getMovieInfoById() {
                    var id = "abc";
                    webTestClient
                            .get()
                            .uri(MOVIES_INFO_URL + "/{id}", id)
                            .exchange()
                            .expectStatus()
                            .is2xxSuccessful()
                            /*.expectBody(MovieInfo.class)
                            .consumeWith(movieInfoEntityExchangeResult -> {
                                var movieInfo = movieInfoEntityExchangeResult.getResponseBody();
                                assert movieInfo != null;
                            })*/
                            .expectBody()
                            .jsonPath("$.name").isEqualTo("Dark Knight Rises");

                }
              }
          ```