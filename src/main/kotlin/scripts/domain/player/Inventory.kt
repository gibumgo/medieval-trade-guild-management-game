package scripts.domain.player

import scripts.domain.Item.ItemSlot
import scripts.domain.Item.ItemSlots
import scripts.domain.common.Capacity

class Inventory(
    var items: ItemSlots,
    var capacity: Capacity
) {
    private val BASE_INVENTORY_SALARY_PER_DAY = 5

    init {
        updateCapacity()
    }

    private fun updateCapacity() {
        this.capacity = capacity.currentUpdate(items.totalWeight())
    }

    fun addItems(newItems: List<ItemSlot>) {
        items = items.addAll(newItems)
        updateCapacity()
    }

    fun removeItems(newItems: List<ItemSlot>) {
        items = items.removeAll(newItems)
        updateCapacity()
    }

    fun allItems(): List<ItemSlot> = items.allItems()

    fun calculateCost(): Int = capacity.costOfCurrent(BASE_INVENTORY_SALARY_PER_DAY)
}