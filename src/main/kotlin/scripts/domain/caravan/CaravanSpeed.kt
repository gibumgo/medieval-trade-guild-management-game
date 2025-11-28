package scripts.domain.caravan

@JvmInline
value class CaravanSpeed private constructor(
    val speed: Int
) {
    fun travelPerDay() : Int = speed

    companion object {
        fun from(speed: Int) = CaravanSpeed(speed)
    }
}
