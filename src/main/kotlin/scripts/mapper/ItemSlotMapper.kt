package scripts.mapper

import scripts.domain.Item.Item
import scripts.domain.Item.ItemSlot
import scripts.domain.Item.ItemSlots
import scripts.domain.reward.Rewards
import scripts.dto.ItemSlotDTO


object ItemSlotMapper {
    fun toDTO(item: ItemSlot): ItemSlotDTO {
        return ItemSlotDTO(
            name = item.item.name,
            weight = item.item.weight.weight,
            quantity = item.quantity,
        )
    }

    fun toDTO(rewards: Rewards): List<ItemSlotDTO> {
        return rewards.itemSlots().map { toDTO(it) }
    }

    fun toDTO(items: ItemSlots): List<ItemSlotDTO> {
        return items.items.map { toDTO(it) }
    }

    fun fromDTO(dto: ItemSlotDTO): ItemSlot {
        return ItemSlot.of(
            Item.of(dto.name, dto.weight),
            dto.quantity
        )
    }

    fun fromDTOs(dtos: List<ItemSlotDTO>): List<ItemSlot> {
        return dtos.map { fromDTO(it) }
    }
}
