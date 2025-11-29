package scripts.mapper

import scripts.domain.quest.QuestDelivery
import scripts.dto.QuestDeliveryDTO
import scripts.dto.CaravanDTO


object QuestDeliveryMapper {
    fun toDTO(
        delivery: QuestDelivery
    ): QuestDeliveryDTO {

        return QuestDeliveryDTO(
            quest = QuestMapper.toDTO(delivery.quest),
            caravan = CaravanDTO(
                name = delivery.caravan.name,
                leader = delivery.caravan.leader,
                speed = delivery.caravan.speed.speed,
                maxCapacity = delivery.caravan.maxCapacity.weight,
                maintenanceCost = delivery.caravan.maintenanceCost.amount,
                status = delivery.caravan.status.displayName,
            ),
            progressDay = delivery.progressDay.currentDay.day,
            totalDays = delivery.progressDay.totalTravelDays.day
        )
    }

    fun toDTOs(quests: List<QuestDelivery>): List<QuestDeliveryDTO> {
        return quests.map { toDTO(it) }
    }
}
