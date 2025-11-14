package scripts.domain.common

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import scripts.domain.Inventory.InventoryItem

class InventoryItemTest {
    private lateinit var baseItem: Item

    @BeforeEach
    fun setUp() {
        // 공통 Item 초기화
        baseItem = Item.of("밀", 1)
    }
    @Test
    @DisplayName("정상적인 아이템 수량 생성")
    fun createTest() {
        val inventoryItem = InventoryItem.of(baseItem, 10)
        assertEquals(inventoryItem, InventoryItem.of(baseItem, 10))
    }

    @Test
    @DisplayName("수량 0인 아이템 생성 가능")
    fun zeroItemTest() {
        val inventoryItem = InventoryItem.of(baseItem, 0)
        assertEquals(0, inventoryItem.quantity)
    }

    @Test
    @DisplayName("음수 item 생성 시 예외 발생")
    fun negativeitemTest() {
        assertThrows<IllegalArgumentException> {
            InventoryItem.of(baseItem, -1)
        }
    }
}
