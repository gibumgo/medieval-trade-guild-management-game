package scripts.domain.supply

import scripts.domain.common.InventoryItem
import scripts.domain.player.Player

class SupplyBox(
    val type: SupplyBoxType,
    val rewards: List<InventoryItem>
) {
    private fun generateRewards(): List<InventoryItem> {
        return rewards
    }

    fun canBePurchasedBy(player: Player): Boolean {
        return player.canAffordSupplyBox(type)
    }

    fun purchaseBy(player: Player): List<InventoryItem> {
        require(canBePurchasedBy(player)) { "구매 불가" }
        player.pay(type.cost)
        val rewards = generateRewards()
        player.addItems(rewards)
        return rewards
    }
}
