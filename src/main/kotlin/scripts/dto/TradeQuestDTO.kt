package scripts.dto

import kotlinx.serialization.Serializable

@Serializable
data class TradeQuestDTO(
    val city: String,
    val status: String,
    val requiredItems: List<ItemSlotDTO>,
    val rewardGold: Int,
    val rewardReputation: Int,
    val durationDays: Int
)
