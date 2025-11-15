package scripts.domain.player

import scripts.domain.Inventory.InventoryItemDTO
import scripts.domain.caravan.CaravanDTO


data class PlayerDTO(
    val gold: Int,
    val reputation: Int,
    val capacityMax: Int,
    val capacityUsed: Int,
    val inventory: List<InventoryItemDTO>,
    val caravans: List<CaravanDTO>
)
