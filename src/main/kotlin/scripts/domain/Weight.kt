package scripts.domain

@JvmInline
value class Weight private constructor(val weight: Int) {

    init {
        validPositive(weight)
    }

    private fun validPositive(weight : Int) {
        require(weight >= INITIAL_LEGHT) { ErrorMessage.WEIGHT_ERROR }
    }

    fun plus(other: Weight): Weight {
        return Weight(weight + other.weight)
    }

    fun minus(other: Weight): Weight {
        return Weight(weight - other.weight)
    }

    fun isZero(): Boolean = weight == INITIAL_LEGHT

    fun isOver(max: Weight): Boolean {
        return weight >= max.weight
    }

    fun isInRange(max: Weight): Boolean = weight in INITIAL_LEGHT..max.weight

    companion object {
        private val INITIAL_LEGHT = 0

        fun of(weight: Int): Weight {
            return Weight(weight)
        }
    }
}

