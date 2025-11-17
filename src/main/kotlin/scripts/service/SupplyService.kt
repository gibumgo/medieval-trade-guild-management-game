package scripts.service

import scripts.domain.common.ItemSlot
import scripts.domain.common.Item
import scripts.domain.reward.Reward
import scripts.domain.supply.SupplyBox
import scripts.domain.supply.SupplyBoxType

class SupplyService {
    fun allSupplyType(): List<SupplyBoxType> {
        return SupplyBoxType.entries
    }

//    fun allSupplyBoxes(): List<SupplyBox> {
//        return SupplyBoxType.values().map { type ->
//            createBoxByType(type)
//        }
//    }

    private fun createBoxByType(type: SupplyBoxType): SupplyBox {
        val wheat: List<ItemSlot> = listOf(ItemSlot.of(Item.of("밀", 1), 10))

        return when (type) {
            SupplyBoxType.BASIC ->
                SupplyBox.of(type, Reward.ofItems(wheat))

            SupplyBoxType.ADVANCED ->
                SupplyBox.of(type, wheat)

            SupplyBoxType.ROYAL ->
                SupplyBox.of(type, wheat)

            SupplyBoxType.LEGENDARY ->
                SupplyBox.of(type, wheat)
        }
    }
}
