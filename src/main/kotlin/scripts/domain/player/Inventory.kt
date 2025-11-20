package scripts.domain.player

import scripts.domain.Item.ItemSlot
import scripts.domain.Item.ItemSlots
import scripts.domain.common.Capacity

class Inventory(
    var items: ItemSlots,
    var capacity: Capacity
) {
    init {
        validMaxCapacity()
    }

    private fun validMaxCapacity() {
        this.capacity = this.capacity.currentUpdate(items.totalWeight())
    }

    fun addItems(newItems: List<ItemSlot>) {
        newItems.forEach { newItem -> items = items.add(newItem) }
    }

    fun removeItems(newItems: List<ItemSlot>) {
        newItems.forEach { newItem -> items = items.remove(newItem) }
    }

    fun allItems(): List<ItemSlot> = items.allItems()
}