package scripts.domain.common

import scripts.domain.player.Player

data class Reward private constructor(
    val gold: Gold,
    val reputation: ReputationPoint,
    val items: List<InventoryItem>
) {

    companion object {
        fun of(gold: Gold, point: ReputationPoint): Reward = Reward(gold, point, emptyList())

        fun of(items: List<InventoryItem>): Reward = Reward(Gold.empty(), ReputationPoint.empty(), items)
    }
}
