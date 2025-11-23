package scripts.domain.common

import scripts.domain.ErrorMessage

@JvmInline
value class ReputationPoint private constructor(val point: Int) {

    init {
        validMoneyAmount(this.point)
    }

    private fun validMoneyAmount(point: Int) {
        require(point >= MIN_AMOUNT) { ErrorMessage.REPUTATION_POINT_ERROR }
    }

    fun isAffordable(otherPoint: ReputationPoint): Boolean = point >= otherPoint.point

    fun increase(otherPoint: ReputationPoint) = ReputationPoint(point + otherPoint.point)

    fun isPositive(): Boolean = point > MIN_AMOUNT

    companion object {
        private val MIN_AMOUNT = 0

        fun of(amount: Int) = ReputationPoint(amount)

        fun empty() = ReputationPoint(MIN_AMOUNT)
    }
}
