package scripts.application.mapper

import scripts.domain.Inventory.InventoryItem
import scripts.dto.InventoryItemDTO


object InventoryItemMapper {
    fun toDTO(item: InventoryItem): InventoryItemDTO {
        return InventoryItemDTO(
            name = item.item.name,
            weight = item.item.weight.weight,
            quantity = item.quantity,
        )
    }
}
