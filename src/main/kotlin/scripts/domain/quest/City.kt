package scripts.domain.quest

import kotlin.math.ceil

data class City(
    val name: String,
    val distance: Int
) {
    fun calculateTravelTime(speed: Int): Int {
        return ceil(distance.toDouble() / speed).toInt()
    }
}
