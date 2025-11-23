package scripts.domain.player

import scripts.domain.common.Gold
import scripts.domain.Item.ItemSlot
import scripts.domain.caravan.Caravan
import scripts.domain.common.ReputationPoint
import scripts.domain.reward.Rewards

class Player(
    var playerStatus: PlayerStatus,
    var inventory: Inventory,
    var caravans: List<Caravan> = emptyList(),
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

    fun updateCaravan(otherCaravan: Caravan) {
        this.caravans = this.caravans.map { replaceCaravan(it, otherCaravan) }
    }

    private fun replaceCaravan(currentCaravan: Caravan, otherCaravan: Caravan): Caravan {
        if (currentCaravan.name == otherCaravan.name) {
            return otherCaravan
        }
        return currentCaravan
    }

    fun caravansLength(): Int {
        return caravans.size
    }

    fun calculateCost(): Int {
        return inventory.calculateCost()
    }

    companion object {
        private const val MIN_SPEED = 1
    }
}
