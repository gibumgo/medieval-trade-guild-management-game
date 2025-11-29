

- 의문점
도메인 객체 안에 로직을 넣으라면서…
그럼 Service Layer는 언제 만들지?
서비스가 너무 많아지면 God service 되고, 없애면 도메인이 비대해지는 것 같은데?

- 나중에 추가 기능
저장소(Repository) 로부터 데이터를 가져와 검증해야 하는 로직
ex)
퀘스트 완료 조건 확인 시
플레이어 인벤토리, 캐러밴 상태, 현재 날짜 등 여러 요소 필요한 경우

11.16 todo
- TradeQuest 구조 개선
    Quest 완료 처리 흐름 정리 (CaravanStatus, progressDay 포함)
- InventoryItem 의 역할 재정의 (VO vs Entity)
    QuestRequiredItem 같은 별도 타입 고민해보기

11.17
- 파일 추가 고민
supply_reward_config.json or SupplyBox_config 통합
- 각 상자 타입에 맞는 리워드 생성기 클래스 이름
- {
  "BASIC": "BasicRewardGenerator",
  "ADVANCED": "AdvancedRewardGenerator",
  "ROYAL": "RoyalRewardGenerator",
  "LEGENDARY": "LegendaryRewardGenerator"
  }

11.18
- 더미 아이템 퀘스트 추가 x
- 퀘스트 서비스 레이어 추가 x
- 입출력 요구 사항 수정 x

---
11.21
- 기존 3주차 추가 기능 구현, 조언 받은 후 일정 배정

- 리펙토링 목록
  - Reward 아이템 목록 -> 일급컬렉션으로 변경
    - 인벤토리 addItems, removeItems 파라미터 번경
- 추가 기능 목록
  - 행상대 적재 용량 기능 - 퀘스트 필요 아이템 용량이 행상대 적재 용량보다 작아야 미션을 수행할 수 있다.
  - 입력 받은 값이 틀리면 재입력 받는다
  - 게임 종료 조건
- 퀘스트 관련 테스트 구현
- 인벤토리 최대 용량 예외 처리 확인하는 테스트 추가 하기

- 플레이어 객체 역할 -> 레파지토리로 나누기
- - json 파싱 스케쥴 확인하기


11.26
-퀘스트 객체 - 단순 위임 메소드 리펙터링
    totalRewardGold(): Gold = rewards.totalGold()
    totalRewardReputation()  = rewards.totalReputation()
이런 메서드가 많아지면 도메인의 책임이 불필요하게 커지고
View/DTO 편의를 위해 도메인이 오염될 위험이 커질거라 예상된다.

게터를 사용하지 않는 다고 단순히 넘어갔던 위임 메소드
퀘스트 테스트 코드를 모든 함수마다 만들고 리펙토링하는 과정에서
퀘스트는 이렇게 거대한 객체가  아닌데 테스트할 게 많아졌지? 라는 생각이 들었다.
그래서 함수 하나씩 어떤 역할 때문에 만들어졌는지 고민하기 시작했다.

dto에서 게터를 쓰지 않으려고 만들어진 함수, 결국 도메인에 View 관심사가 섞임
게터를 쓰지 않는다고 상관 없던 게 아니었다.
보상 관련 책임은 Reward에게 전체 값이니까 일급컬렉션으로 위임.

베드 스멜
1. 뷰 요구가 늘어남 → 도메인에 또다른 편의 메서드가 추가
예: 화면에서 요구 아이템 수량도 화면에 바로 띄우고 싶다 → 도메인에 이런 메서드가 생김
fun requiredItemCount(): Int = requiredItems.count()

2. 다른 화면에서 “포맷팅된 문자열”이 필요해짐
   fun formattedReward(): String = "${totalRewardGold()} G / ${totalRewardReputation()} RP"
이 순간, 도메인이 문자열을 다루기 시작한다.
→ 화면 변경이 도메인 변경을 야기하게 됨

- 퀘스트 객체 불변 객체로 만들기
- QuestRepositoryImpl 모든 퀘스트 불러와서 업데이트 메소드 만들기


11.29
- 배달 용량 초과시 처리 문제 고민 -> 예외처리, 메세지, 혹은 선택 비활성화
- QuestRepository 분리 -> 전체 퀘스트, 플레이어 퀘스트 (퀘스트 히스토리 따로 저장할지 고민)
