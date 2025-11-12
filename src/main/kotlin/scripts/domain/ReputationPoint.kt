package scripts.domain

class ReputationPoint private constructor(val point : Int) {
    private val MIN_AMOUNT = 0

    init {
        validMoneyAmount(this.point)
    }

    private fun validMoneyAmount(point: Int) {
        require(point > MIN_AMOUNT) { ErrorMessage.REPUTATION_POINT_ERROR }
    }

    companion object {
        fun of(amount: Int): ReputationPoint {
            return ReputationPoint(amount);
        }
    }
}
