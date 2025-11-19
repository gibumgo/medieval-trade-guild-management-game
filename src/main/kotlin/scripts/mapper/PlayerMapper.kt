package scripts.mapper

import scripts.domain.player.Player
import scripts.dto.PlayerDTO

object PlayerMapper {

    fun toDTO(player: Player): PlayerDTO {
        return PlayerDTO(
            gold = player.playerStatus.gold.amount,
            reputation = player.playerStatus.reputationPoint.point,
            capacityMax = player.capacity.max.weight,
            capacityUsed = player.capacity.current.weight,
            inventory = player.inventory.items.map { ItemSlotMapper.toDTO(it) },
            caravans = player.caravans.map { CaravanMapper.toDTO(it) }
        )
    }
}
