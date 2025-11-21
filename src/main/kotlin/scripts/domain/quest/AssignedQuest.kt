package scripts.domain.quest

import scripts.domain.caravan.Caravan
import scripts.domain.reward.Reward


class AssignedQuest private constructor(
    val quest: TradeQuest,
    val caravan: Caravan,
    var progressDay: Int = START_DAY,
) {
    fun progressOneDay(): AssignedQuest {
        val nextProgressDay = this.progressDay + DAY_STEP
        if (isNextFinished(nextProgressDay)) {
            quest.complete()
            return AssignedQuest(quest, caravan.complete(), nextProgressDay)
        }
        return AssignedQuest(quest, caravan, nextProgressDay)
    }

    private fun isNextFinished(day: Int): Boolean = this.progressDay + DAY_STEP == totalDays()

    fun totalDays(): Int {
        return caravan.travelDaysFor(quest);
    }

    fun isCompleted(): Boolean {
        return progressDay == totalDays()
    }

    fun completeCaravan(): Caravan {
        return caravan.resetToReady()
    }

    fun getReward(): Reward {
        return quest.reward()
    }

    companion object {
        private const val DAY_STEP = 1
        private const val START_DAY = 0

        fun of(quest: TradeQuest, caravan: Caravan): AssignedQuest {
            return AssignedQuest(quest, caravan.startTrip(), START_DAY)
        }
    }
}