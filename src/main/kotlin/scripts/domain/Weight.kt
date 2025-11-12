package scripts.domain

@JvmInline
value class Weight private constructor(val weight: Int) {

    init {
        validPositive(weight)
    }

    private fun validPositive(weight : Int) {
        require(weight >= INITIAL_LEGHT) { ErrorMessage.WEIGHT_ERROR }
    }

    companion object {
        private val INITIAL_LEGHT = 0

        fun of(weight: Int): Weight {
            return Weight(weight)
        }
    }
}

