package scripts.domain.common

import scripts.domain.ErrorMessage

@JvmInline
value class Gold private constructor(val amount: Int) {
    init {
        validMoneyAmount(this.amount)
    }

    private fun validMoneyAmount(amount: Int) {
        require(amount >= MIN_AMOUNT) { ErrorMessage.MONEY_ERROR }
    }

    fun minus(other: Gold): Gold = Gold(amount - other.amount)

    fun isAffordable(otherGold: Gold): Boolean = amount >= otherGold.amount

    companion object {
        private val MIN_AMOUNT: Int = 0

         fun of(amount: Int): Gold {
            return Gold(amount);
        }
    }
}
