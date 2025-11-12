package scripts.domain

@JvmInline
value class ReputationPoint private constructor(val point : Int) {

    init {
        validMoneyAmount(this.point)
    }

    private fun validMoneyAmount(point: Int) {
        require(point > MIN_AMOUNT) { ErrorMessage.REPUTATION_POINT_ERROR }
    }

    companion object {
        private val MIN_AMOUNT = 0

        fun of(amount: Int): ReputationPoint {
            return ReputationPoint(amount);
        }
    }
}
