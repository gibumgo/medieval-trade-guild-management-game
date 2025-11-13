package scripts.domain.supply

import scripts.domain.common.Gold
import scripts.domain.common.InventoryItem
import scripts.domain.common.ReputationPoint

class SupplyBox(
    val type: SupplyBoxType,
    val rewards: List<InventoryItem>
) {
    fun canPurchase(playerGold: Gold, playerReputation: ReputationPoint): Boolean {
        return type.canPurchase(playerGold, playerReputation)
    }

    fun generateRewards(): List<InventoryItem> {
        return rewards
    }
}
