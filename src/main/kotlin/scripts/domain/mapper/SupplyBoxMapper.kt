package scripts.domain.mapper

import scripts.domain.supply.SupplyBox
import scripts.dto.SupplyBoxDTO

object SupplyBoxMapper {
    fun toDTO(supplyBox: SupplyBox): SupplyBoxDTO {
        return SupplyBoxDTO(
            displayName = supplyBox.type.name,
            price = supplyBox.type.cost.amount,
            minReputation = supplyBox.type.minReputationPoint.point
        )
    }
}
