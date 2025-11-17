package scripts.domain.testObject

import scripts.domain.common.Item
import scripts.domain.common.ItemSlot

object TestItems {
    val wheat = listOf(
        ItemSlot.of(Item.of("밀", 1), 10)
    )
}
