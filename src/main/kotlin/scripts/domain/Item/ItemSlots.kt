package scripts.domain.Item

class ItemSlots private constructor(
    val items: List<ItemSlot>
) {
    fun hasItems(otherItems: List<ItemSlot>): Boolean =
        otherItems.isNotEmpty() && otherItems.all { hasFindItem(it) }

    private fun hasFindItem(otherItem: ItemSlot): Boolean =
        items.any { it.hasQuantity(otherItem) }

    fun allItems(): List<ItemSlot> = items.toList()

    fun totalWeight(): Weight =
        items.fold(Weight.empty()) { totalWeight, item ->
            totalWeight.plus(item.weight())
        }

    fun add(newItem: ItemSlot): ItemSlots {
        val updatedItems = items.map { it.increaseSameItem(newItem) }
        val finalItems = updatedItems + listOfNotNull(
            newItem.takeUnless { updatedItems.any { it.isSameItem(newItem) } }
        )
        return ItemSlots(finalItems)
    }

    fun remove(newItem: ItemSlot): ItemSlots {
        val updatedItems = items.map { it.decreaseSameItem(newItem) }
        return ItemSlots(updatedItems)
    }

    companion object {
        fun of(items: List<ItemSlot>): ItemSlots {
            return ItemSlots(items)
        }
    }
}

