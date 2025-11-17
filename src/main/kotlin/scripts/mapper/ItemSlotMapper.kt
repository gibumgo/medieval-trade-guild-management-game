package scripts.application.mapper

import scripts.domain.common.ItemSlot
import scripts.dto.ItemSlotDTO


object ItemSlotMapper {
    fun toDTO(item: ItemSlot): ItemSlotDTO {
        return ItemSlotDTO(
            name = item.item.name,
            weight = item.item.weight.weight,
            quantity = item.quantity,
        )
    }
}
