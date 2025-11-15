package scripts.dto

data class TradeQuestDTO(
    val city: String,
    val status: String,
    val requiredItems: List<InventoryItemDTO>,
    val rewardGold: Int,
    val rewardReputation: Int
)
