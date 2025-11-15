package scripts.domain.caravan

data class CaravanDTO(
    val name: String,
    val leader: String,
    val speed: Int,
    val maxCapacity: Int,
    val maintenanceCost: Int,
    val status: String
)
