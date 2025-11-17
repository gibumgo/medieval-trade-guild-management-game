package scripts.domain.common

import scripts.domain.Inventory.InventoryItem
import scripts.domain.player.Player

data class Reward private constructor(
    val gold: Gold,
    val reputation: ReputationPoint,
    val items: List<InventoryItem>
) {
    fun applyTo(player: Player) {
        player.addGold(gold)
        player.increaseReputation(reputation)
        player.addItems(items)
    }

    companion object {
        fun ofQuestReward(
            gold: Gold,
            point: ReputationPoint
        ): Reward = Reward(gold, point, emptyList())

        fun ofItems(
            items: List<InventoryItem>
        ): Reward = Reward(Gold.empty(), ReputationPoint.empty(), items)
    }
}
