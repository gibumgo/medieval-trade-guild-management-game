package scripts.domain.reward

import scripts.domain.Inventory.InventoryItem
import scripts.domain.common.Gold
import scripts.domain.common.ReputationPoint
import scripts.domain.player.Player

data class Reward private constructor(
    private val gold: Gold,
    private val reputation: ReputationPoint,
    private val items: List<InventoryItem>
) {
    fun applyTo(player: Player) {
        player.addGold(gold)
        player.increaseReputation(reputation)
        player.addItems(items)
    }

    companion object {
        fun of(
            gold: Gold,
            point: ReputationPoint,
            items: List<InventoryItem>
        ): Reward = Reward(gold, point, items.toList())

        fun ofQuestReward(
            gold: Gold,
            point: ReputationPoint
        ): Reward = Reward(gold, point, emptyList())

        fun ofItems(
            items: List<InventoryItem>
        ): Reward = Reward(Gold.Companion.empty(), ReputationPoint.Companion.empty(), items.toList())
    }
}