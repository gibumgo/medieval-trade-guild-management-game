package scripts.domain.common

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

    fun increase(amount: Int): ItemSlot {
        return ItemSlot(this.item, this.quantity + amount)
    }

    fun decrease(amount: Int): ItemSlot {
        require(quantity >= amount) { "재고 부족: $item" }
        return ItemSlot(this.item, this.quantity - amount)
    }

    companion object {
        private val MIN_AMOUNT: Int = 0

        fun of(item: Item, quantity: Int): ItemSlot = ItemSlot(item, quantity)
    }
}