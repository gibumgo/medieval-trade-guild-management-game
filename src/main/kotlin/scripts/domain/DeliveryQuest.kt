package scripts.domain

import scripts.domain.common.City
import scripts.domain.common.Gold
import scripts.domain.common.InventoryItem
import scripts.domain.common.ReputationPoint
import scripts.domain.common.Reward

class DeliveryQuest(
    val city: City,
    val requiredItems: List<InventoryItem>,
    val gold: Gold,
    val reputation: ReputationPoint,
) {

    fun grant(): Reward {
        return Reward.of(gold, reputation)
    }
}
