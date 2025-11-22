package scripts.dto

import kotlinx.serialization.Serializable

@Serializable
data class ItemSlotDTO(
    val name: String,
    val weight: Int,
    val quantity: Int,
)
