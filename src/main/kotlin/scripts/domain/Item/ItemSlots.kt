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

    companion object {
        fun of(items: List<ItemSlot>): ItemSlots {
            return ItemSlots(items)
        }
    }
}

