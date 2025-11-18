package scripts.domain.testObject

import scripts.domain.common.Item
import scripts.domain.common.ItemSlot

object TestItems {
    val wheat = listOf(
        ItemSlot.of(Item.of("밀", 1), 10)
    )

    val wood = ItemSlot.of(Item.of("목재", 1), 5)
    val spice = ItemSlot.of(Item.of("향료", 1), 2)
    val iron = ItemSlot.of(Item.of("철", 1), 20)

}
