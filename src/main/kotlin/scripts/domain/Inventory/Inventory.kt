package scripts.domain.Inventory

import scripts.domain.common.ItemSlot

class Inventory(
     val items: MutableList<ItemSlot> = mutableListOf()
) {
    fun allItems(): List<ItemSlot> = items.toList()

    fun hasItems(requiredItems: List<ItemSlot>): Boolean =
        requiredItems.all { hasItem(it) }

    private fun hasItem(required: ItemSlot): Boolean =
        items.any { it.hasQuantity(required) }

    fun addAll(newItems: List<ItemSlot>) {
        newItems.forEach { newItem ->
            addOrIncreaseItem(newItem)
        }
    }

    private fun addOrIncreaseItem(newItem: ItemSlot) {
        findItem(newItem)?.let { increaseItem(it, newItem.quantity) } ?: addNewItem(newItem)
    }

    private fun increaseItem(existingItem: ItemSlot, amount: Int) {
        val updated = existingItem.increase(amount)
        replaceItem(existingItem, updated)
    }

    private fun addNewItem(newItem: ItemSlot) {
        items.add(newItem)
    }

    private fun replaceItem(oldItem: ItemSlot, newItem: ItemSlot) {
        items.remove(oldItem)
        items.add(newItem)
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
