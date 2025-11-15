package scripts.domain.time

interface GameTime {
    fun advance()
    fun currentDay(): Int
}
