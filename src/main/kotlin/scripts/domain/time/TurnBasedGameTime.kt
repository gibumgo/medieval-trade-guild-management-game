package scripts.domain.time

class TurnBasedGameTime : GameTime {
    private var day: Int = START_DAY

    override fun advance() {
        day += DAY_INCREMENT
    }

    override fun currentDay(): Int = day

    companion object {
        private const val START_DAY = 1
        private const val DAY_INCREMENT = 1
    }
}
