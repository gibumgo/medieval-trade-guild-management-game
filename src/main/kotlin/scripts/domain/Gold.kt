package scripts.domain

@JvmInline
value class Gold private constructor(val amount: Int) {
    init {
        validMoneyAmount(this.amount)
    }

    private fun validMoneyAmount(amount: Int) {
        require(amount > MIN_AMOUNT) { ErrorMessage.MONEY_ERROR }
    }

    companion object {
        private val MIN_AMOUNT: Int = 0

         fun of(amount: Int): Gold {
            return Gold(amount);
        }
    }
}
