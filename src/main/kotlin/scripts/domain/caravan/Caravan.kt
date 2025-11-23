package scripts.domain.caravan

import scripts.domain.common.Gold
import scripts.domain.Item.Weight
import scripts.domain.quest.TradeQuest

class Caravan private constructor(
    val name: String,
    val leader: String,
    val speed: Int,
    val maxCapacity: Weight,
    val maintenanceCost: Gold,
    val status: CaravanStatus
) {

    fun isReady(): Boolean = status == CaravanStatus.READY

    fun isComplete(): Boolean = status == CaravanStatus.COMPLETED

    fun travelDaysFor(quest: TradeQuest): Int {
        return quest.calculateDurationBy(this.speed)
    }

    fun startTrip(): Caravan =
        Caravan(name, leader, speed, maxCapacity, maintenanceCost, CaravanStatus.TRAVELING)

    fun complete(): Caravan =
        Caravan(name, leader, speed, maxCapacity, maintenanceCost, CaravanStatus.COMPLETED)

    fun resetToReady(): Caravan =
        Caravan(name, leader, speed, maxCapacity, maintenanceCost, CaravanStatus.READY)


    companion object {
        fun of(
            name: String,
            leader: String,
            speed: Int,
            maxCapacity: Weight,
            maintenanceCost: Gold,
            status: CaravanStatus
        ): Caravan {
            return Caravan(name, leader, speed, maxCapacity, maintenanceCost, status)
        }

    }
}
