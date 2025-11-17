package scripts.service

import scripts.domain.Inventory.InventoryItem
import scripts.domain.common.Item
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
        val wheat: List<InventoryItem> = listOf(InventoryItem.of(Item.of("밀", 1), 10))

        return when (type) {
            SupplyBoxType.BASIC ->
                SupplyBox(type, wheat)

            SupplyBoxType.ADVANCED ->
                SupplyBox(type, listOf())

            SupplyBoxType.ROYAL ->
                SupplyBox(type, listOf())

            SupplyBoxType.LEGENDARY ->
                SupplyBox(type, listOf())
        }
    }
}
