package scripts.domain.supply

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import scripts.domain.Item.Item
import scripts.domain.Item.ItemSlot
import scripts.domain.common.Gold
import scripts.domain.common.ReputationPoint
import scripts.domain.reward.Reward


class SupplyBoxTest {
    private lateinit var items: List<ItemSlot>
    private lateinit var box: SupplyBox

    @BeforeEach
    fun setup() {
        items = listOf(ItemSlot.of(Item.of("밀", 1), 2))

        box = SupplyBox.of(SupplyBoxType.BASIC) {
            Reward.of(
                gold = Gold.of(500),
                point = ReputationPoint.of(10),
                items = items,
            )
        }
    }

    @Test
    @DisplayName("골드, 명성, 아이템이 충분하면 보급 상자 구매 성공")
    fun purchase() {
        val reward = box.purchaseBy()

        assertEquals(500, reward.gold.amount)
        assertEquals(10, reward.reputation.point)
        assertEquals(items, reward.items)
    }
}
