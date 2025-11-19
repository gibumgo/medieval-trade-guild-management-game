package scripts.domain.quest

import scripts.domain.caravan.Caravan
import scripts.domain.common.Gold
import scripts.domain.common.ReputationPoint
import scripts.domain.reward.Reward



class AssignedQuest private constructor(
    val quest: TradeQuest,
    val caravan: Caravan,
    private var progressDay: Int = START_DAY,
) {
    fun progressOneDay() =
        AssignedQuest(quest, caravan, this.progressDay + DAY_STEP)

    fun totalDays(): Int {
        return caravan.travelDaysFor(quest);
    }

    // 문제 부분
    fun completed(): Reward {
        quest.transitionToCompleted()
        caravan.finishTrip()
        return Reward.ofQuestReward(Gold.empty(), ReputationPoint.empty())
    }

    fun isCompleted(): Boolean = quest.isCompleted()

    fun resetToReady() {
        caravan.resetToReady()
    }

    companion object {
        private const val DAY_STEP = 1
        private const val START_DAY = 0

        fun of(quest: TradeQuest, caravan: Caravan) =
            AssignedQuest(quest, caravan, START_DAY)
    }
}