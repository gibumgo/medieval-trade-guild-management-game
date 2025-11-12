package scripts.domain

import kotlin.Int

data class Capacity private constructor(val current: Int, val max: Int) {
    init {
        validMaxLength(max)
        validOverCapacity(current, max)
    }

    private fun validOverCapacity(current: Int, max: Int) {
        require(current in MIN_CAPACITY..max) { ErrorMessage.CAPACITY_OVER_ERROR }
    }

    private fun validMaxLength(max: Int) {
        require(max >= MIN_CAPACITY) { ErrorMessage.CAPACITY_MAX_ERROR }
    }


    companion object {
        private const val MIN_CAPACITY = 0

        public fun of(current: Int, max: Int): Capacity {
            return Capacity(current,max)
        }
    }
}
