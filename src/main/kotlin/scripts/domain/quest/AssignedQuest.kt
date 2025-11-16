package scripts.domain.quest

import scripts.domain.caravan.Caravan
import scripts.domain.common.Reward
import scripts.domain.player.Player

class AssignedQuest private constructor(
    val quest: TradeQuest,
    val caravan: Caravan,
    var progressDay: Int,
) {
    fun complete(): Reward {
        return quest.complete()
    }

    fun progress() {
        this.progressDay += progressDay
    }

    fun totalDays(): Int {
        return caravan.calculateDistance(quest);
    }

    companion object {
        fun of(quest: TradeQuest, caravan: Caravan): AssignedQuest {
            caravan.startTrip()
            return AssignedQuest(quest, caravan, 0)
        }
    }
}