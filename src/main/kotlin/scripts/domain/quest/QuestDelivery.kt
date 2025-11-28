package scripts.domain.quest

import scripts.domain.caravan.Caravan
import kotlin.math.ceil

class QuestDelivery private constructor(
    val quest: Quest,
    val caravan: Caravan,
    val progressDay: QuestProgressDay,
) {
    fun progressOneDay(): QuestDelivery = QuestDelivery(quest, caravan, progressDay.nextDay())

    fun completedQuest(): Quest {
        require(progressDay.isCompleted()) { "퀘스트가 아직 진행중 입니다." }
        return quest.complete()
    }

    fun completedCaravan(): Caravan {
        require(progressDay.isCompleted()) { "쿼스트가 아직 진행중 입니다." }
        return caravan.complete()
    }

    companion object {
        fun of(quest: Quest, caravan: Caravan): QuestDelivery {
            val totalDay = getTotalDay(quest, caravan)
            return QuestDelivery(quest, caravan, QuestProgressDay.start(totalDay))
        }

        private fun getTotalDay(quest: Quest, caravan: Caravan): Int =
            ceil(quest.city.distance.toDouble() / caravan.speed).toInt()
    }
}
