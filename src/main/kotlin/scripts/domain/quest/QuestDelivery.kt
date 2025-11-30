package scripts.domain.quest

import scripts.domain.caravan.Caravan

class QuestDelivery private constructor(
    val quest: Quest,
    val caravan: Caravan,
    val progressDay: QuestProgressDay,
) {
    fun progressOneDay(): QuestDelivery = QuestDelivery(quest, caravan, progressDay.nextDay())

    fun completedQuest(): QuestDelivery {
        if (isCompletedProgress()){
            return QuestDelivery(quest.complete(), caravan.complete(), progressDay)
        }
        return this
    }

    fun returnCaravan() = caravan.resetToReady()

    fun isCompletedProgress(): Boolean = progressDay.isCompleted()

    companion object {
        fun of(quest: Quest, caravan: Caravan): QuestDelivery {
            val totalDay = quest.calculateTravelTime(caravan.currentSpeed())
            return QuestDelivery(quest, caravan, QuestProgressDay.start(totalDay))
        }
    }
}
