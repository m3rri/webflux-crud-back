# webflux-crud-back
analysis 현황을 모니터링 할 수 있는 api
## API 설명
### GET /analysis/{invokeUser}
* invokeUser를 url path 매개변수로 받아 아래 두 개의 Flux 객체를 merge하여 지속적으로 client에 push
  * invokeUser로 검색되는 entity list (sinks로 emit되기전의 analysis list를 받아옴)
  * Map<String, Sinks> 객체에서 key=invokeUser로 가져온 sinks 객체
* `ServerSentEvent.builder(dtoObject).build()`를 통해서 method의 return type을 `Flux<ServerSentEvent<dtoObject>>`로 만들 수 있다
  * 별도로 HeaderType을 지정하지 않아도 request가 계속 열려있게 된다
### POST /analysis/{invokeUser}/{id}
* invokeUser, id, algorithm 3개의 매개변수를 받아서 analysis의 create -> update 과정을 sinks에 emit
  1. `addAnalysis` 작업 이후 persist 된 entity를 `doOnNext`의 콜백 파라미터로 받으면 sinks에 emit한다
  2. algorithm별 step update 회수를 조회하여 `updateAnalysis` 호출하고 (이 메소드는 Flux를 리턴) 작업이 한 번 실행될 때마다 chain으로 추가한 `doOnNext` 콜백함수가 실행된다
  3. `updateAnalysis` 에서는 Mono.repeat가 실행되며 이때 `Flux<Integer>`를 리턴. 따라서 2번의 콜백함수에서 `getAnalysis` 메소드를 호출하여 update 이후의 상태를 체크하여 sinks로 emit
* update sql을 trigger하는 메소드를 실행할때는 해당 객체를 구독해줘야 (`.subscribe()` 실행) 메소드가 정상적으로 실행된다
  [(출처 링크)](https://stackoverflow.com/questions/61717654/update-using-query-not-updating-data-in-reactivecrudrepository)  
  이유는 아직 파악하지 못함 조금 더 공부해야됨
## 실행하는 front source
[간단한 html+js script 로 구현한 front](https://github.com/m3rri/webflux-crud-front)
