package scripts.dto

data class QuestDeliveryDTO(
    val quest: QuestDTO,
    val caravan: CaravanDTO,
    val progressDay: Int,
    val totalDays: Int,
)

