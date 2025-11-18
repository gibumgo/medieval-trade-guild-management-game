package scripts.mapper

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
            requiredItems = ItemSlotMapper.toDTO(quest.requiredItems),
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
            requiredItems = ItemSlotMapper.toDTO(quest.requiredItems),
            rewardGold = quest.gold.amount,
            rewardReputation = quest.reputation.point,
            durationDays = 0
        )
    }

    fun toDTOs(quests: List<TradeQuest>): List<TradeQuestDTO> {
        return quests.map { toDTO(it) }
    }

    fun toDTOs(quests: List<TradeQuest>, player: Player): List<TradeQuestDTO> {
        return quests.map { toDTO(it, player) }
    }
}
