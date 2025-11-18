package scripts.domain.Item

class ItemSlots private constructor(val items: List<ItemSlot>) {
    fun allItems(): List<ItemSlot> = items.toList()

    fun hasItems(requiredItems: List<ItemSlot>): Boolean =
        requiredItems.all { hasItem(it) }

    private fun hasItem(required: ItemSlot): Boolean =
        items.any { it.hasQuantity(required) }


    private fun findItem(item: ItemSlot): ItemSlot? =
        items.find { it.isSameItem(item) }

    companion object {
        fun of (items: List<ItemSlot>): ItemSlots{
            return ItemSlots(items)
        }
    }
}

