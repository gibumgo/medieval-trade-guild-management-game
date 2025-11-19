package scripts.domain.Item

import scripts.domain.ErrorMessage

data class ItemSlot private constructor(val item: Item, val quantity: Int) {
    init {
        validPositive(this.quantity)
    }

    private fun validPositive(quantity: Int) {
        require(quantity >= MIN_AMOUNT) { ErrorMessage.QUANTITY_ERROR }
    }

    fun hasQuantity(otherItem : ItemSlot): Boolean{
        return isSameItem(otherItem) && quantity >= otherItem.quantity
    }

    fun isSameItem(other: ItemSlot) = this.item == other.item

    fun increaseSameItem(otherItem: ItemSlot): ItemSlot {
        if (this.isSameItem(otherItem)) {
            return ItemSlot(this.item, this.quantity + otherItem.quantity)
        }
        return this
    }

    fun decreaseSameItem(otherItem: ItemSlot): ItemSlot {
        if (this.isSameItem(otherItem)) {
            return ItemSlot(this.item, this.quantity - otherItem.quantity)
        }
        return this
    }

    fun weight(): Weight {
        return this.item.weight(quantity)
    }

    companion object {
        private val MIN_AMOUNT: Int = 0

        fun of(item: Item, quantity: Int): ItemSlot = ItemSlot(item, quantity)
    }
}