package scripts.mapper

import scripts.domain.Item.Weight
import scripts.domain.caravan.Caravan
import scripts.domain.caravan.CaravanStatus
import scripts.domain.common.Gold
import scripts.dto.CaravanDTO

object CaravanMapper {
    fun toDTO(
        caravan: Caravan,
    ): CaravanDTO {
        return CaravanDTO(
            name = caravan.name,
            leader = caravan.leader,
            speed = caravan.speed.speed,
            maxCapacity = caravan.maxCapacity.weight,
            maintenanceCost = caravan.maintenanceCost.amount,
            status = caravan.status.displayName
        )
    }

    fun fromDTO(dto: CaravanDTO): Caravan {
        return Caravan.of(
            name = dto.name,
            leader = dto.leader,
            speed = dto.speed,
            maxCapacity = Weight.of(dto.maxCapacity),
            maintenanceCost = Gold.of(dto.maintenanceCost),
            status = CaravanStatus.READY
        )
    }

    fun toDTOs(caravans: List<Caravan>): List<CaravanDTO> {
        return caravans.map { toDTO(it) }
    }
}
