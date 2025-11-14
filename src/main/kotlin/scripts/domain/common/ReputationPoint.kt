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

    fun isAffordable(otherPoint: ReputationPoint): Boolean {
        return point >= otherPoint.point
    }

    fun increase(otherPoint: ReputationPoint) = ReputationPoint(point + otherPoint.point)

    companion object {
        private val MIN_AMOUNT = 0

        fun of(amount: Int): ReputationPoint {
            return ReputationPoint(amount);
        }

        fun empty(): ReputationPoint {
            return ReputationPoint(MIN_AMOUNT);
        }
    }
}
