package scripts.mapper

import scripts.domain.supply.SupplyBoxType
import scripts.dto.SupplyBoxDTO

object SupplyBoxMapper {
    fun toDTO(supplyBox: SupplyBoxType): SupplyBoxDTO {
        return SupplyBoxDTO(
            displayName = supplyBox.name,
            price = supplyBox.cost.amount,
            minReputation = supplyBox.minReputationPoint.point
        )
    }

    fun toDTOs(types: List<SupplyBoxType>): List<SupplyBoxDTO> {
        return types.map { toDTO(it) }
    }
}
