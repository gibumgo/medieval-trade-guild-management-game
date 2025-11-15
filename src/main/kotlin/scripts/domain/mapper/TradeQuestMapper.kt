package scripts.application.mapper

import scripts.domain.quest.TradeQuest
import scripts.domain.quest.QuestStatus
import scripts.dto.TradeQuestDTO

object TradeQuestMapper {
    fun toDTO(
        quest: TradeQuest,
        status: QuestStatus
    ): TradeQuestDTO {

        return TradeQuestDTO(
            city = quest.city.name,
            status = status.name,
            requiredItems = quest.requiredItems.map { InventoryItemMapper.toDTO(it) },
            rewardGold = quest.gold.amount,
            rewardReputation = quest.reputation.point
        )
    }
}
