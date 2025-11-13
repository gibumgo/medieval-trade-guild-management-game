package scripts.domain.supply

import scripts.domain.Gold
import scripts.domain.InventoryItem
import scripts.domain.ReputationPoint

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
