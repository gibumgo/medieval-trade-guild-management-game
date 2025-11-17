package scripts.application.mapper

import scripts.domain.player.Player
import scripts.dto.PlayerDTO

object PlayerMapper {

    fun toDTO(player: Player): PlayerDTO {
        return PlayerDTO(
            gold = player.gold.amount,
            reputation = player.reputationPoint.point,
            capacityMax = player.capacity.max.weight,
            capacityUsed = player.capacity.current.weight,
            inventory = player.inventory.items.map { InventoryItemMapper.toDTO(it) },
            caravans = player.caravans.map { CaravanMapper.toDTO(it) }
        )
    }
}
