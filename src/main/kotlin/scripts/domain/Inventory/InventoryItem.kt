package scripts.domain.Inventory

import scripts.domain.ErrorMessage
import scripts.domain.common.Item

data class InventoryItem private constructor(val item: Item, val quantity: Int) {
    init {
        validPositive(this.quantity)
    }

    private fun validPositive(quantity: Int) {
        require(quantity >= MIN_AMOUNT) { ErrorMessage.QUANTITY_ERROR }
    }

    companion object {
        private val MIN_AMOUNT: Int = 0

        fun of(item: Item, quantity: Int): InventoryItem = InventoryItem(item, quantity)
    }
}