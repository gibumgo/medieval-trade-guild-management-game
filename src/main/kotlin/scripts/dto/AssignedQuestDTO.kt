package scripts.dto

data class AssignedQuestDTO(
    val quest: TradeQuestDTO,
    val caravan: CaravanDTO,
    val progressDay: Int,
    val totalDays: Int,
)

