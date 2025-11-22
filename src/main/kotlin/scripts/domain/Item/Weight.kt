package scripts.domain.Item

import scripts.domain.ErrorMessage

@JvmInline
value class Weight private constructor(val weight: Int) {
    init {
        validPositive(weight)
    }

    private fun validPositive(weight : Int) {
        require(weight >= INITIAL_LENGTH) { ErrorMessage.WEIGHT_ERROR }
    }

    fun plus(other: Weight) = Weight(weight + other.weight)

    fun minus(other: Weight) =  Weight(weight - other.weight)

    fun multiply(other: Int) =  Weight(weight * other)

    fun costIncurredBy(rate: Int): Int  = weight * rate

    fun isZero(): Boolean = weight == INITIAL_LENGTH

    fun isOver(max: Weight): Boolean  =  weight >= max.weight

    fun isInRange(max: Weight): Boolean = weight in INITIAL_LENGTH..max.weight

    companion object {
        private val INITIAL_LENGTH = 0

        fun of(weight: Int): Weight {
            return Weight(weight)
        }

        fun empty(): Weight {
            return Weight(0)
        }
    }
}