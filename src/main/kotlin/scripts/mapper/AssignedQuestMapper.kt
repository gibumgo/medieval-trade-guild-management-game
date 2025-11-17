package scripts.mapper

import scripts.application.mapper.TradeQuestMapper
import scripts.domain.quest.AssignedQuest
import scripts.dto.AssignedQuestDTO
import scripts.dto.CaravanDTO


object AssignedQuestMapper {
    fun toDTO(
        assigned: AssignedQuest,
    ): AssignedQuestDTO {

        return AssignedQuestDTO(
            quest = TradeQuestMapper.toDTO(assigned.quest),
            caravan = CaravanDTO(
                name = assigned.caravan.name,
                leader = assigned.caravan.leader,
                speed = assigned.caravan.speed,
                maxCapacity = assigned.caravan.maxCapacity.weight,
                maintenanceCost = assigned.caravan.maintenanceCost.amount,
                status = assigned.caravan.status.name,
            ),
            progressDay = assigned.progressDay,
            totalDays = assigned.totalDays()
        )
    }
}
