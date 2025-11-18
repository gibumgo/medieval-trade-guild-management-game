package scripts.mapper

import scripts.domain.caravan.Caravan
import scripts.dto.CaravanDTO

object CaravanMapper {
    fun toDTO(
        caravan: Caravan,
    ): CaravanDTO {
        return CaravanDTO(
            name = caravan.name,
            leader = caravan.leader,
            speed = caravan.speed,
            maxCapacity = caravan.maxCapacity.weight,
            maintenanceCost = caravan.maintenanceCost.amount,
            status = caravan.status.name
        )
    }

    fun toDTOs(caravans: List<Caravan>): List<CaravanDTO> {
        return caravans.map { toDTO(it) }
    }
}
