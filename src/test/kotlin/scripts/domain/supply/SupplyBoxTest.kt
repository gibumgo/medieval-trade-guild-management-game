package scripts.domain.supply

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import scripts.domain.common.Capacity
import scripts.domain.common.Gold
import scripts.domain.common.InventoryItem
import scripts.domain.common.Item
import scripts.domain.common.ReputationPoint
import scripts.domain.player.Player

class SupplyBoxTest {
    private lateinit var item: Item
    private lateinit var box: SupplyBox
    private lateinit var player: Player

    @BeforeEach
    fun setUp() {
        item = Item.of("밀", 1)
        val rewards = listOf(InventoryItem.of(item, 2))
        box = SupplyBox(SupplyBoxType.BASIC, rewards)

        player = Player(
            gold = Gold.of(1000),
            reputationPoint = ReputationPoint.of(0),
            inventory = emptyList(),
            capacity = Capacity.of(0,500)
        )
    }

    @Test
    @DisplayName("골드 충분 시 박스 구매 성공")
    fun purchaseSuccessTest() {
        val rewards = box.purchaseBy(player)

        assertEquals(2, rewards[0].quantity)
        assertEquals("밀", rewards[0].item.name)
        assertEquals(500, player.gold.amount)
    }

    @Test
    @DisplayName("골드 부족 시 예외 발생")
    fun purchaseFailInsufficientGold() {
        val poorPlayer = Player(
            gold = Gold.of(100),
            reputationPoint = ReputationPoint.of(0),
            inventory = emptyList(),
            capacity = Capacity.of(0,500)
        )

        val exception = assertThrows<IllegalArgumentException> {
            box.purchaseBy(poorPlayer)
        }

        assertEquals("구매 불가", exception.message)
    }
}