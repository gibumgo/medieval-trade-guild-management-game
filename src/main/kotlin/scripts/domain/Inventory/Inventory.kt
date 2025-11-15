package scripts.domain.Inventory

class Inventory(
     val items: MutableList<InventoryItem> = mutableListOf()
) {
    fun allItems(): List<InventoryItem> = items.toList()

    fun hasItems(requiredItems: List<InventoryItem>): Boolean =
        requiredItems.all { hasItem(it) }

    private fun hasItem(required: InventoryItem): Boolean =
        items.any { it.hasQuantity(required) }

    fun addAll(newItems: List<InventoryItem>) {
        newItems.forEach { newItem ->
            addOrIncreaseItem(newItem)
        }
    }

    private fun addOrIncreaseItem(newItem: InventoryItem) {
        findItem(newItem)?.let { increaseItem(it, newItem.quantity) } ?: addNewItem(newItem)
    }

    private fun increaseItem(existingItem: InventoryItem, amount: Int) {
        val updated = existingItem.increase(amount)
        replaceItem(existingItem, updated)
    }

    private fun addNewItem(newItem: InventoryItem) {
        items.add(newItem)
    }

    private fun replaceItem(oldItem: InventoryItem, newItem: InventoryItem) {
        items.remove(oldItem)
        items.add(newItem)
    }

    fun removeItems(requiredItems: List<InventoryItem>) {
        requiredItems.forEach { required ->
            val currentItem = items.find { it.isSameItem(required) }
                ?: throw IllegalArgumentException("재고 없음: ${required.item}")
            val decreased = currentItem.decrease(required.quantity)
            items.remove(currentItem)
            if (decreased.quantity > 0) items.add(decreased)
        }
    }

    private fun findItem(item: InventoryItem): InventoryItem? =
        items.find { it.isSameItem(item) }
}
