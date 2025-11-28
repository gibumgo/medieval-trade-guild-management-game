package scripts.domain.caravan

import scripts.domain.common.Gold
import scripts.domain.Item.Weight
import scripts.domain.quest.TradeQuest

class Caravan private constructor(
    val name: String,
    val leader: String,
    val speed: CaravanSpeed,
    val maxCapacity: Weight,
    val maintenanceCost: Gold,
    val status: CaravanStatus
) {

    fun isReady(): Boolean = status == CaravanStatus.READY

    fun isComplete(): Boolean = status == CaravanStatus.COMPLETED

    fun travelDaysFor(quest: TradeQuest): Int {
        return quest.minTravelDays(this.speed.speed)
    }

    fun currentSpeed(): Int {
        return speed.travelPerDay()
    }

    fun startTrip(): Caravan = copyWithStatus(CaravanStatus.TRAVELING)

    fun complete(): Caravan = copyWithStatus(CaravanStatus.COMPLETED)

    fun resetToReady(): Caravan = copyWithStatus(CaravanStatus.READY)

    private fun copyWithStatus(newStatus: CaravanStatus) =
        Caravan(name, leader, speed, maxCapacity, maintenanceCost, newStatus)

    companion object {
        fun of(
            name: String,
            leader: String,
            speed: Int,
            maxCapacity: Weight,
            maintenanceCost: Gold,
            status: CaravanStatus
        ): Caravan {
            return Caravan(name, leader, CaravanSpeed.from(speed), maxCapacity, maintenanceCost, status)
        }

    }
}
