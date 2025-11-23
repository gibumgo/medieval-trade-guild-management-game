package scripts.domain.quest

import scripts.domain.caravan.Caravan
import scripts.domain.reward.Rewards

class AssignedQuest private constructor(
    val quest: TradeQuest,
    val caravan: Caravan,
    var progressDay: Int = START_DAY,
) {
    fun progressOneDay(): AssignedQuest {
        val nextProgressDay = this.progressDay + DAY_STEP
        if (isNextFinished(nextProgressDay)) {
            quest.complete()
        }
        return AssignedQuest(quest, caravan, nextProgressDay)
    }

    fun totalDays(): Int = caravan.travelDaysFor(quest)

    private fun isNextFinished(day: Int): Boolean = day == totalDays()

    fun isCompleted(): Boolean = progressDay == totalDays()

    fun getReward(): Rewards = quest.rewards

    fun caravanLeader() : String = caravan.leader

    companion object {
        private const val DAY_STEP = 1
        private const val START_DAY = 0

        fun of(quest: TradeQuest, caravan: Caravan): AssignedQuest {
            return AssignedQuest(quest, caravan.startTrip(), START_DAY)
        }
    }
}
