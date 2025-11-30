package scripts.domain.quest

import scripts.domain.common.GameDay

class QuestProgressDay private constructor(
    val currentDay: GameDay,
    val totalTravelDays: GameDay,
) {
    init {
        validateTravelLength()
    }

    private fun validateTravelLength() {
        require(currentDay.isNotAfter(totalTravelDays)) { "진행 날짜는 총 여행일 보다 미만 이여야 합니다." }
    }

    fun nextDay(): QuestProgressDay = QuestProgressDay(currentDay.nextDay(), totalTravelDays)

    fun isCompleted(): Boolean = currentDay.isSameDay(totalTravelDays)

    companion object {
        fun start(totalTravelDays: Int) =
            QuestProgressDay(GameDay.start(), GameDay.from(totalTravelDays))
    }
}
