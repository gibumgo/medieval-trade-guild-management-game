package scripts.util

object GameText {
    //// 스타트 / 일일 보고
    const val DAY_REPORT_TITLE = "상단 보고"
    const val CURRENT_INVENTORY = "현재 보유 재고 :"
    const val CURRENT_INVENTORY_EMPTY = "없음"
    const val INVENTORY_UPDATE = "창고 업데이트"

    //// NPC
    const val GUILD_MASTER_NAME = "길드 관리관"
    const val GUILD_MASTER_GREETING = "\"오늘도 행운을 빕니다. 보급 상자는 어떤 걸 드릴까요?\""

    //// 보급 상자
    const val SUPPLY_BOX_OPTIONS = "선택 가능한 보급 상자:"
    const val SUPPLY_SELECT_PROMPT = "(번호 선택, 0 = 모두 거절) >"
    const val SUPPLY_WARNING_NOT_ENOUGH = "[경고] 귀하의 금화 혹은 명성이 부족합니다."
    const val SUPPLY_OPEN_SUCCESS = "보급 상자를 열었습니다!"
    const val SUPPLY_ITEMS_RECEIVED = "획득: {items}"

    //// 거래 의뢰서
    const val QUEST_REPORT_TITLE = "오늘의 거래 의뢰서"
    const val QUEST_NO_SELECTION = "[경고] 숫자를 입력해주세요."
    const val QUEST_REJECTED = "모든 의뢰를 거절하였습니다."
    const val QUEST_DISPATCHED = "[파견 현황]"
    const val QUEST_COMPLETE = "[퀘스트 완료]"
    const val QUEST_MIN_DURATION = "최소 소요 기간: {days}일"

    //// 진행 메시지
    const val ASSIGNED_QUEST_DEPART = "출발 중..."
    const val SELECT_CARAVAN_PROMPT = "배정할 행상대를 선택하세요"
    const val NO_ONGOING_QUESTS = "현재 진행 중인 퀘스트가 없습니다."

    //// 하루 종료
    const val DAY_END_TITLE = "DAY {day} 종료"
    const val DAY_END_COST = "창고 유지비 및 급료   : -{cost} G"
    const val DAY_END_CURRENT_GOLD = "현재 보유 골드         : {gold}"
    const val DAY_END_SPECIAL_EVENT_NONE = "오늘은 특별한 사건이 없었습니다."
    const val DAY_END_NEXT = "다음 날 진행하려면 Enter (종료 = 0) >"

    //// UI
    const val LINE_SEPARATOR = "--------------------------------------------------"
    const val LINE_DOUBLE_SEPARATOR = "=================================================="

    //// 플레이어 상태 라벨
    const val LABEL_GOLD = "골드"
    const val LABEL_REPUTATION = "명성"
    const val LABEL_CAPACITY = "창고"
    const val LABEL_CARAVAN_COUNT = "행상대 보유"
}