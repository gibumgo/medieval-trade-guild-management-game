package scripts.domain.player

import scripts.domain.Item.Inventory
import scripts.domain.common.Capacity
import scripts.domain.common.Gold
import scripts.domain.Item.ItemSlot
import scripts.domain.caravan.Caravan
import scripts.domain.reward.Reward

class Player(
    var playerStatus: PlayerStatus,
    var capacity: Capacity,
    var inventory: Inventory,
    var caravans: List<Caravan> = emptyList()
) {
    fun pay(amount: Gold) {
        this.playerStatus = this.playerStatus.payGold(amount)
    }

    fun removeItems(requiredItems: List<ItemSlot>) {
        inventory.removeItems(requiredItems)
    }

    fun earnReward(reward: Reward) {
        this.playerStatus = reward.applyTo(playerStatus)
        reward.applyTo(this.inventory)
    }

    fun availableCaravanMaxSpeed(): Int {
        return availableCaravans().maxOfOrNull { it.speed } ?: MIN_SPEED
    }

    fun availableCaravans(): List<Caravan> {
        return this.caravans.filter { it.isReady() }
    }

    fun currentGold(): Int {
        return playerStatus.gold.amount
    }

    fun allItems(): List<ItemSlot> = inventory.allItems()

    companion object {
        private const val MIN_SPEED = 1
    }
}
