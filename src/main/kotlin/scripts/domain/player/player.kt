package scripts.domain.player

import scripts.domain.Item.Inventory
import scripts.domain.common.Capacity
import scripts.domain.common.Gold
import scripts.domain.Item.ItemSlot
import scripts.domain.caravan.Caravan
import scripts.domain.common.ReputationPoint
import scripts.domain.reward.Reward
import scripts.domain.supply.SupplyBoxType

class Player(
    var playerStatus: PlayerStatus,
    var capacity: Capacity,
    var inventory: Inventory,
    var caravans: List<Caravan> = emptyList()
) {
    fun pay(amount: Gold) {
        this.playerStatus = playerStatus.payGold(amount)
    }

    fun canAffordSupplyBox(boxType: SupplyBoxType): Boolean {
        return boxType.canPurchase(this.playerStatus)
    }

    fun addGold(otherGold: Gold) {
        this.gold = this.gold.plus(otherGold)
    }

    fun increaseReputation(otherPoint: ReputationPoint) {
        this.reputationPoint = reputationPoint.increase(otherPoint)
    }

    fun addItems(rewards: List<ItemSlot>) {
        this.inventory.addAll(rewards)
    }

    fun removeItems(requiredItems: List<ItemSlot>) {
        inventory.removeItems(requiredItems)
    }

    fun earnReward(reward: Reward) {
        reward.applyTo(this)
    }

    fun availableCaravanMaxSpeed(): Int {
        return availableCaravans().maxOfOrNull { it.speed } ?: MIN_SPEED
    }

    fun availableCaravans(): List<Caravan> {
        return this.caravans.filter { it.isReady() }
    }

    fun currentGold(): Int {
        return gold.amount
    }

    fun allItems(): List<ItemSlot> = inventory.allItems()

    companion object {
        private const val MIN_SPEED = 1
    }
}
