package scripts.domain.quest

import scripts.domain.caravan.Caravan
import scripts.domain.reward.Reward


class AssignedQuest private constructor(
    val quest: TradeQuest,
    val caravan: Caravan,
    var progressDay: Int = START_DAY,
) {
    fun progressOneDay() =
        AssignedQuest(quest, caravan, this.progressDay + DAY_STEP)

    fun totalDays(): Int {
        return caravan.travelDaysFor(quest);
    }

    fun isCompleted(): Boolean = progressDay >= totalDays()

    fun checkComplete() {
        if (progressDay == totalDays()) {
            quest.complete()
            caravan.complete()
        }
    }

    fun getReward(): Reward {
        caravan.resetToReady()
        return quest.reward()
    }

    companion object {
        private const val DAY_STEP = 1
        private const val START_DAY = 0

        fun of(quest: TradeQuest, caravan: Caravan) =
            AssignedQuest(quest, caravan, START_DAY)
    }
}