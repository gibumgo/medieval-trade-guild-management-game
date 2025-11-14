package scripts.domain.supply

import scripts.domain.common.InventoryItem
import scripts.domain.common.Reward
import scripts.domain.player.Player

class SupplyBox(
    val type: SupplyBoxType,
    val rewards: List<InventoryItem>
) {
    private fun generateRewards(): List<InventoryItem> {
        return rewards
    }

    fun purchaseBy(player: Player): Reward {
        validPurchase(player)
        return Reward.of(generateRewards())
    }

    private fun validPurchase(player: Player) {
        require(player.canAffordSupplyBox(type)) { "구매 불가" }
    }
}
