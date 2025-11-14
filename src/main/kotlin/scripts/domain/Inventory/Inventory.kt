package scripts.domain.Inventory

class Inventory(
    private val items: MutableList<InventoryItem> = mutableListOf()
) {
    fun allItems(): List<InventoryItem> = items.toList()

    fun hasItems(requiredItems: List<InventoryItem>): Boolean =
        requiredItems.all { hasItem(it) }

    private fun hasItem(required: InventoryItem): Boolean =
        items.any { it.hasQuantity(required) }

    private fun findItem(item: InventoryItem): InventoryItem? =
        items.find { it.isSameItem(item) }
}
