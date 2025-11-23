package scripts.domain.reward

import scripts.domain.Item.ItemSlot
import scripts.domain.common.Gold
import scripts.domain.common.ReputationPoint
import scripts.domain.player.Player

sealed class Reward {
    data class GoldReward(val gold: Gold) : Reward()

    data class ReputationReward(val reputation: ReputationPoint) : Reward()

    data class ItemReward(val items: List<ItemSlot>) : Reward()

    data class CompositeReward(val rewards: List<Reward>) : Reward()

    object None : Reward()

    fun applyTo(player: Player) {
        when (this) {
            is GoldReward -> player.increaseGold(gold)
            is ReputationReward -> player.increaseReputation(reputation)
            is ItemReward -> player.addItems(items)
            is CompositeReward -> rewards.forEach { it.applyTo(player) }
            None -> Unit
        }
    }

    companion object Factory {
        fun of(
            gold: Gold = Gold.empty(),
            reputation: ReputationPoint = ReputationPoint.empty(),
            items: List<ItemSlot> = emptyList()
        ): List<Reward> =
            listOfNotNull(
                gold.toReward(),
                reputation.toReward(),
                items.toReward()
            )

        private fun Gold.toReward(): Reward? =
            takeIf { isPositive() }?.let { GoldReward(it) }

        private fun ReputationPoint.toReward(): Reward? =
            takeIf { isPositive() }?.let { ReputationReward(it) }

        private fun List<ItemSlot>.toReward(): Reward? =
            takeIf { isNotEmpty() }?.let { ItemReward(it) }
    }
}