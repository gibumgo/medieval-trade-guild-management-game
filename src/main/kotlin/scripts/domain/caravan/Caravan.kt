package scripts.domain.caravan

import scripts.domain.common.Gold
import scripts.domain.common.Weight

data class Caravan(
    val name: String,
    val leader: String,
    val speed :  Int,
    val maxCapacity : Weight,
    val maintenanceCost : Gold
){

}
