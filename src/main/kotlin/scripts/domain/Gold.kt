package scripts.domain

class Gold private constructor(val amount: Int) {
    private val MIN_AMOUNT = 0

    init {
        validMoneyAmount(this.amount)
    }

    private fun validMoneyAmount(amount: Int) {
        require(amount > MIN_AMOUNT) { ErrorMessage.MONEY_ERROR }
    }

    companion object {
         fun of(amount: Int): Gold {
            return Gold(amount);
        }
    }
}
