package scripts.mapper

import scripts.domain.Item.ItemSlots
import scripts.domain.common.Gold
import scripts.domain.common.ReputationPoint
import scripts.domain.quest.City
import scripts.domain.quest.Quest
import scripts.dto.QuestDTO

object QuestMapper {
    fun toDTO(
        quest: Quest,
        speed: Int
    ): QuestDTO {

        return QuestDTO(
            city = quest.city.name,
            status = quest.status.description,
            requiredItems = ItemSlotMapper.toDTO(quest.requiredItems),
            rewardGold = quest.rewards.totalGold().amount,
            rewardReputation = quest.rewards.totalReputation().point,
            durationDays = quest.calculateTravelTime(speed)
        )
    }

    fun toDTO(
        quest: Quest,
    ): QuestDTO {

        return QuestDTO(
            city = quest.city.name,
            status = quest.status.description,
            requiredItems = ItemSlotMapper.toDTO(quest.requiredItems),
            rewardGold = quest.rewards.totalGold().amount,
            rewardReputation = quest.rewards.totalReputation().point,
            durationDays = 0
        )
    }

    fun fromDTO(dto: QuestDTO): Quest {
        return Quest.of(
            city = City(dto.city, dto.durationDays),
            requiredItems = ItemSlots.of(ItemSlotMapper.fromDTOs(dto.requiredItems)),
            gold = Gold.of(dto.rewardGold),
            reputation = ReputationPoint.of(dto.rewardReputation)
        )
    }

    fun toDTOs(quests: List<Quest>, speed: Int): List<QuestDTO> {
        return quests.map { toDTO(it, speed) }
    }
}
