package scripts.application.mapper

import scripts.domain.caravan.Caravan
import scripts.domain.caravan.CaravanStatus
import scripts.dto.CaravanDTO

object CaravanMapper {
    fun toDTO(
        caravan: Caravan,
        status: CaravanStatus? = null,
    ): CaravanDTO {
        return CaravanDTO(
            name = caravan.name,
            leader = caravan.leader,
            speed = caravan.speed,
            maxCapacity = caravan.maxCapacity.weight,
            maintenanceCost = caravan.maintenanceCost.amount,
            status = status?.name ?: "UNKNOWN"
        )
    }
}
