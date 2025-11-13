package scripts.domain.player

import scripts.domain.common.Capacity
import scripts.domain.common.Gold
import scripts.domain.common.InventoryItem
import scripts.domain.common.ReputationPoint

class player (
    var gold: Gold,
    var reputationPoint: ReputationPoint,
    var capacity: Capacity,
    inventory : List<InventoryItem>
){
    private val inventoryItem : MutableList<InventoryItem> = inventory.toMutableList()

    fun pay(amount : Gold) : Gold = gold.minus(amount)
}
