package scripts.mapper

import scripts.domain.Item.ItemSlots
import scripts.domain.common.Gold
import scripts.domain.common.ReputationPoint
import scripts.domain.player.Player
import scripts.domain.quest.City
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
            rewardGold = quest.totalRewardGold().amount,
            rewardReputation = quest.totalRewardReputation().point,
            durationDays = quest.minTravelDays(
                player.availableCaravanMaxSpeed()
            )
        )
    }

    fun toDTO(
        quest: TradeQuest,
    ): TradeQuestDTO {

        return TradeQuestDTO(
            city = quest.city.name,
            status = quest.status.name,
            requiredItems = ItemSlotMapper.toDTO(quest.requiredItems),
            rewardGold = quest.totalRewardGold().amount,
            rewardReputation = quest.totalRewardReputation().point,
            durationDays = 0
        )
    }

    fun fromDTO(dto: TradeQuestDTO): TradeQuest {
        return TradeQuest.of(
            city = City(dto.city, dto.durationDays),
            requiredItems = ItemSlots.of(ItemSlotMapper.fromDTOs(dto.requiredItems)),
            gold = Gold.of(dto.rewardGold),
            reputation = ReputationPoint.of(dto.rewardReputation)
        )
    }

    fun toDTOs(quests: List<TradeQuest>, player: Player): List<TradeQuestDTO> {
        return quests.map { toDTO(it, player) }
    }
}
