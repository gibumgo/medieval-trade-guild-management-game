package scripts.domain.player

import scripts.domain.common.Capacity
import scripts.domain.common.Gold
import scripts.domain.Inventory.InventoryItem
import scripts.domain.caravan.Caravan
import scripts.domain.common.ReputationPoint
import scripts.domain.supply.SupplyBoxType

class Player(
    var gold: Gold,
    var reputationPoint: ReputationPoint,
    var capacity: Capacity,
    var inventory: List<InventoryItem> = emptyList(),
    var caravans: List<Caravan> = emptyList()
) {
    private val inventoryItems: MutableList<InventoryItem> = inventory.toMutableList()
    private val playerCaravans: MutableList<Caravan> = caravans.toMutableList()

    fun pay(amount: Gold) {
        this.gold = gold.minus(amount)
    }

    fun canAffordSupplyBox(boxType: SupplyBoxType): Boolean {
        return boxType.canPurchase(gold, reputationPoint)
    }

    fun addItems(rewards: List<InventoryItem>) {
        this.inventoryItems.addAll(rewards)
    }

    fun addGold(otherGold: Gold) {
        this.gold = this.gold.plus(otherGold)
    }

    fun increaseReputation(otherPoint: ReputationPoint) {
        this.reputationPoint = reputationPoint.increase(otherPoint)
    }
}
