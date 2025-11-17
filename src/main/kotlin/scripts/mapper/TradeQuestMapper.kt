package scripts.application.mapper

import scripts.domain.player.Player
import scripts.domain.quest.TradeQuest
import scripts.dto.TradeQuestDTO

object TradeQuestMapper {
    fun toDTO(
        quest: TradeQuest,
        player: Player,
    ): TradeQuestDTO {

        return TradeQuestDTO(
            city = quest.city.name,
            status = quest.status.name,
            requiredItems = quest.requiredItems.map { InventoryItemMapper.toDTO(it) },
            rewardGold = quest.gold.amount,
            rewardReputation = quest.reputation.point,
            durationDays = quest.calculateMaxDuration(player)
        )
    }

    fun toDTO(
        quest: TradeQuest,
    ): TradeQuestDTO {

        return TradeQuestDTO(
            city = quest.city.name,
            status = quest.status.name,
            requiredItems = quest.requiredItems.map { InventoryItemMapper.toDTO(it) },
            rewardGold = quest.gold.amount,
            rewardReputation = quest.reputation.point,
            durationDays = 0
        )
    }
}
