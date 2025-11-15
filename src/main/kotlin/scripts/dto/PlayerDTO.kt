package scripts.dto

data class PlayerDTO(
    val gold: Int,
    val reputation: Int,
    val capacityMax: Int,
    val capacityUsed: Int,
    val inventory: List<InventoryItemDTO>,
    val caravans: List<CaravanDTO>
)
