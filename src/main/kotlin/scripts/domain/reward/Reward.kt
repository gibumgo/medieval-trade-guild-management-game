package scripts.domain.reward

import scripts.domain.player.Inventory
import scripts.domain.Item.ItemSlot
import scripts.domain.common.Gold
import scripts.domain.common.ReputationPoint
import scripts.domain.player.PlayerStatus

data class Reward private constructor(
    val gold: Gold,
    val reputation: ReputationPoint,
    val items: List<ItemSlot>
) {
    fun applyTo(playerStatus: PlayerStatus): PlayerStatus =
        playerStatus.increase(gold, reputation)

    fun applyTo(inventory: Inventory) {
        inventory.addAll(items)
    }

    companion object {
        fun of(
            gold: Gold,
            point: ReputationPoint,
            items: List<ItemSlot>
        ): Reward = Reward(gold, point, items.toList())

        fun ofQuestReward(
            gold: Gold,
            point: ReputationPoint
        ): Reward = Reward(gold, point, emptyList())

        fun ofItems(
            items: List<ItemSlot>
        ): Reward = Reward(Gold.empty(), ReputationPoint.empty(), items.toList())
    }
}