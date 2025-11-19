package scripts.mapper

import scripts.domain.player.Player
import scripts.dto.PlayerDTO

object PlayerMapper {

    fun toDTO(player: Player): PlayerDTO {
        return PlayerDTO(
            gold = player.playerStatus.gold.amount,
            reputation = player.playerStatus.reputationPoint.point,
            capacityMax = player.inventory.capacity.max.weight,
            capacityUsed = player.inventory.capacity.current.weight,
            inventory = ItemSlotMapper.toDTO(player.inventory.items),
            caravans = player.caravans.map { CaravanMapper.toDTO(it) }
        )
    }
}
