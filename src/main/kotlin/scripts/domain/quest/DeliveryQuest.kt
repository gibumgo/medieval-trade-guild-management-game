package scripts.domain.quest

import scripts.domain.common.City
import scripts.domain.common.Gold
import scripts.domain.Inventory.InventoryItem
import scripts.domain.common.ReputationPoint
import scripts.domain.common.Reward

class DeliveryQuest(
    val city: City,
    val requiredItems: List<InventoryItem>,
    val gold: Gold,
    val reputation: ReputationPoint,
) {

    fun grant(): Reward {
        return Reward.Companion.of(gold, reputation)
    }
}