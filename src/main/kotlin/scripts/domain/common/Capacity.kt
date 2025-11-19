package scripts.domain.common

import scripts.domain.ErrorMessage
import scripts.domain.Item.Weight
import kotlin.Int

data class Capacity private constructor(val current: Weight, val max: Weight) {
    init {
        validOverCapacity(current, max)
    }

    private fun validOverCapacity(current: Weight, max: Weight) {
        require(current.isInRange(max)) { ErrorMessage.CAPACITY_OVER_ERROR }
    }

    fun currentUpdate(current: Weight) : Capacity{
        return of(current, this.max)
    }

    fun remaining(): Weight = max.minus(current)

    fun isFull(): Boolean = current.isOver(max)

    companion object {
        private const val MIN_CAPACITY = 0

        fun of(current: Int, max: Int): Capacity {
            return Capacity(Weight.of(current), Weight.of(max))
        }

        fun of(current: Weight, max: Weight): Capacity {
            return Capacity(current, max)
        }
    }
}
