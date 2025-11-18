package scripts.mapper

import scripts.domain.Item.ItemSlot
import scripts.domain.Item.ItemSlots
import scripts.domain.reward.Reward
import scripts.dto.ItemSlotDTO


object ItemSlotMapper {
    fun toDTO(item: ItemSlot): ItemSlotDTO {
        return ItemSlotDTO(
            name = item.item.name,
            weight = item.item.weight.weight,
            quantity = item.quantity,
        )
    }

    fun toDTO(reward: Reward): List<ItemSlotDTO> {
        return reward.items.map { toDTO(it) }
    }

    fun toDTO(items: ItemSlots): List<ItemSlotDTO> {
        return items.items.map { toDTO(it) }
    }
}
