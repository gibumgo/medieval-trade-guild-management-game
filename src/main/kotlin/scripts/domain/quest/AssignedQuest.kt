package scripts.domain.quest

import scripts.domain.caravan.Caravan
import scripts.domain.reward.Reward

class AssignedQuest private constructor(
    val quest: TradeQuest,
    val caravan: Caravan,
    var progressDay: Int,
) {
    fun progressOneDay() {
        progressDay += 1
    }

    fun totalDays(): Int {
        return caravan.travelDaysFor(quest);
    }

    fun completed(): Reward {
        quest.transitionToCompleted()
        caravan.finishTrip()
        return quest.complete()
    }

    fun isCompleted(): Boolean = quest.isCompleted()

    fun resetToReady() {
        caravan.resetToReady()
    }

    companion object {
        fun of(quest: TradeQuest, caravan: Caravan): AssignedQuest {
            caravan.startTrip()
            return AssignedQuest(quest, caravan, 0)
        }
    }
}