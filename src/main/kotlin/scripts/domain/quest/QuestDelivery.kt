package scripts.domain.quest

import scripts.domain.caravan.Caravan

class QuestDelivery private constructor(
    val quest: Quest,
    val caravan: Caravan,
    val progressDay: QuestProgressDay,
) {
    fun progressOneDay(): QuestDelivery = QuestDelivery(quest, caravan, progressDay.nextDay())

    fun completedQuest(): QuestDelivery {
        require(progressDay.isCompleted()) { "퀘스트가 아직 진행중 입니다." }
        require(progressDay.isCompleted()) { "쿼스트가 아직 진행중 입니다." }
        return QuestDelivery(quest.complete(), caravan.complete(), progressDay)
    }

    fun isCompletedProgress(): Boolean = progressDay.isCompleted()

    companion object {
        fun of(quest: Quest, caravan: Caravan): QuestDelivery {
            val totalDay = quest.calculateTravelTime(caravan.currentSpeed())
            return QuestDelivery(quest, caravan, QuestProgressDay.start(totalDay))
        }
    }
}
