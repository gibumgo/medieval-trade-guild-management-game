

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
11.19
- 기능 구현 목록 다시 재정비
- 테스트 코드 작성
- 미구현 기능 -> 우선순위 배정
- 리펙토링 일정 수정
- 기존 3주차 추가 기능 구현, 조언 받은 후 일정 배정
- json 파싱 스케쥴 확인하기
