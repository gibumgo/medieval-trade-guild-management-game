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
    var gold: Gold,
    var reputationPoint: ReputationPoint,
    var capacity: Capacity,
    var inventory: Inventory,
    var caravans: List<Caravan> = emptyList()
) {
    fun pay(amount: Gold) {
        this.gold = gold.minus(amount)
    }

    fun canAffordSupplyBox(boxType: SupplyBoxType): Boolean {
        return boxType.canPurchase(gold, reputationPoint)
    }

    fun addItems(rewards: List<ItemSlot>) {
        this.inventory.addAll(rewards)
    }

    fun removeItems(requiredItems: List<ItemSlot>) {
        inventory.removeItems(requiredItems)
    }

    fun addGold(otherGold: Gold) {
        this.gold = this.gold.plus(otherGold)
    }

    fun increaseReputation(otherPoint: ReputationPoint) {
        this.reputationPoint = reputationPoint.increase(otherPoint)
    }

    fun earnReward(reward: Reward) {
        reward.applyTo(this)
    }

    fun availableCaravans(): List<Caravan> {
        return this.caravans.filter { it.isReady() }
    }

    fun currentGold(): Int {
        return gold.amount
    }

    fun hasItems(requiredItems: List<ItemSlot>): Boolean {
        return inventory.hasItems(requiredItems)
    }

    fun availableCaravanMaxSpeed(): Int {
        return caravans
            .filter { it.isReady() }
            .maxOfOrNull { it.speed } ?: MIN_SPEED
    }

    fun allItems(): List<ItemSlot> = inventory.allItems()

    companion object {
        private const val MIN_SPEED = 1
    }
}
