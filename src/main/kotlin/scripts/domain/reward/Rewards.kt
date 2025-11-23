package scripts.domain.reward

import scripts.domain.Item.ItemSlot
import scripts.domain.common.Gold
import scripts.domain.common.ReputationPoint
import scripts.domain.player.Player

data class Rewards private constructor(val rewards: List<Reward>) {
    fun totalGold(): Gold =
        rewards.filterIsInstance<Reward.GoldReward>()
            .fold(Gold.empty()) { total, reward -> total.plus(reward.gold) }

    fun totalReputation(): ReputationPoint =
        rewards.filterIsInstance<Reward.ReputationReward>()
            .fold(ReputationPoint.empty()) { total, reward -> total.increase(reward.reputation) }

    fun itemSlots(): List<ItemSlot> =
        rewards.filterIsInstance<Reward.ItemReward>().flatMap { it.items }

    fun applyTo(player: Player) {
        rewards.forEach { it.applyTo(player) }
    }

    companion object {
        fun of(
            gold: Gold = Gold.empty(),
            reputation: ReputationPoint = ReputationPoint.empty(),
            items: List<ItemSlot> = emptyList()
        ) = Rewards(Reward.Factory.of(gold, reputation, items))
    }
}
