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
        validMaxCapacity()
    }

    private fun validMaxCapacity() {
        this.capacity = this.capacity.currentUpdate(items.totalWeight())
    }

    fun addItems(newItems: List<ItemSlot>) {
        newItems.forEach { newItem -> items = items.add(newItem) }
        validMaxCapacity()
    }

    fun removeItems(newItems: List<ItemSlot>) {
        newItems.forEach { newItem -> items = items.remove(newItem) }
        validMaxCapacity()
    }

    fun allItems(): List<ItemSlot> = items.allItems()

    fun calculateCost(): Int = capacity.costOfCurrent(BASE_INVENTORY_SALARY_PER_DAY)
}