package scripts.dto

data class PlayerDTO(
    val gold: Int,
    val reputation: Int,
    val capacityMax: Int,
    val capacityUsed: Int,
    val inventory: List<ItemSlotDTO>,
    val caravans: List<CaravanDTO>
)
