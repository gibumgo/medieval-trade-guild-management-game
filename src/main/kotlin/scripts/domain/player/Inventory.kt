package scripts.domain.player

import scripts.domain.Item.ItemSlot
import scripts.domain.Item.ItemSlots
import scripts.domain.common.Capacity

class Inventory(
    val items: ItemSlots,
    val capacity: Capacity
) {
    init {
        validMaxCapacity()
    }

    private fun validMaxCapacity() {
        this.capacity.currentUpdate(items.totalWeight())
    }

    fun allItems(): List<ItemSlot> = items.allItems()

//    private fun hasItems(required: ItemSlot): Boolean = items.hasItems(required)

    fun addItems(newItems: List<ItemSlot>) {
        newItems.forEach { newItem -> items.add(newItem) }
    }

    fun removeItems(requiredItems: List<ItemSlot>) {
        requiredItems.forEach { required ->
            val currentItem = items.find { it.isSameItem(required) }
                ?: throw IllegalArgumentException("재고 없음: ${required.item}")
            val decreased = currentItem.decrease(required.quantity)
            items.remove(currentItem)
            if (decreased.quantity > 0) items.add(decreased)
        }
    }

    private fun findItem(item: ItemSlot): ItemSlot? =
        items.find { it.isSameItem(item) }
}