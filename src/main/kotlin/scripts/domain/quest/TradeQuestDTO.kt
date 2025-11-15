package scripts.domain.quest

import scripts.domain.Inventory.InventoryItemDTO

data class TradeQuestDTO(
    val city: String,
    val status: String,
    val requiredItems: List<InventoryItemDTO>,
    val rewardGold: Int,
    val rewardReputation: Int
)
