package scripts.domain.caravan

import scripts.domain.common.Gold
import scripts.domain.common.Weight
import scripts.domain.quest.TradeQuest

class Caravan(
    val name: String,
    val leader: String,
    val speed: Int,
    val maxCapacity: Weight,
    val maintenanceCost: Gold,
    private var status: CaravanStatus
) {
    fun currentStatus(): CaravanStatus = status

    fun isReady(): Boolean = status == CaravanStatus.READY

    fun startTrip() {
        this.status = CaravanStatus.TRAVELING
    }

    fun travelDaysFor(quest: TradeQuest): Int {
        return quest.calculateDurationBy(this.speed)
    }
}
