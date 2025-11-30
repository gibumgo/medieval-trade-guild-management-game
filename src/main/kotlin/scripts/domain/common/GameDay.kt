package scripts.domain.common

@JvmInline
value class GameDay private constructor(
    val day: Int
) {
    init {
        validateDay(day)
    }

    private fun validateDay(day: Int) {
        require(day >= START_DAY){"날짜는 음수가 될 수 없습니다."}
    }

    operator fun minus(days: Int): GameDay = GameDay(day - days)
    operator fun minus(other: GameDay): Int = day - other.day

    fun nextDay(): GameDay = GameDay(day + 1)

    fun isBefore(other: GameDay): Boolean = this.day < other.day
    fun isAfter(other: GameDay): Boolean = this.day > other.day
    fun isSameDay(other: GameDay): Boolean = this == other
    fun isNotAfter(other: GameDay): Boolean = this.day <= other.day

    companion object {
        private const val START_DAY = 0

        fun from(day: Int) = GameDay(day)

        fun start() = GameDay(START_DAY)
    }
}
