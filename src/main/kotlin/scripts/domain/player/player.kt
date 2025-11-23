package scripts.domain.player

import scripts.domain.common.Gold
import scripts.domain.Item.ItemSlot
import scripts.domain.common.ReputationPoint
import scripts.domain.reward.Rewards

class Player(
    var playerStatus: PlayerStatus,
    var inventory: Inventory,
) {
    fun pay(amount: Gold) {
        this.playerStatus = this.playerStatus.payGold(amount)
    }

    fun submitItems(requiredItems: List<ItemSlot>) {
        inventory.removeItems(requiredItems)
    }

    fun earnReward(rewards: Rewards) {
        rewards.applyTo(this)
    }

    fun increaseGold(amount: Gold) {
        playerStatus = playerStatus.increaseGold(amount)
    }

    fun increaseReputation(reputation: ReputationPoint) {
        playerStatus = playerStatus.increaseReputation(reputation)
    }

    fun addItems(items: List<ItemSlot>) {
        inventory.addItems(items)
    }

    fun currentGold(): Int {
        return playerStatus.gold.amount
    }

    fun allItems(): List<ItemSlot> = inventory.allItems()

    fun calculateCost(): Int {
        return inventory.calculateCost()
    }
}
