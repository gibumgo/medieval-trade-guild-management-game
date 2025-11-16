package scripts.domain.caravan

import scripts.domain.common.Gold
import scripts.domain.common.Weight

class Caravan(
    val name: String,
    val leader: String,
    val speed :  Int,
    val maxCapacity : Weight,
    val maintenanceCost : Gold,
    private var status: CaravanStatus
){
    fun currentStatus(): CaravanStatus = status

    fun isReady(): Boolean = status == CaravanStatus.READY
}
