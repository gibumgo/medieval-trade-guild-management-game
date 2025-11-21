

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
