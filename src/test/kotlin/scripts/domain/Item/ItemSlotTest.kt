package scripts.domain.Item

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ItemSlotTest {
    private lateinit var baseItem: Item

    @BeforeEach
    fun setUp() {
        // 공통 Item 초기화
        baseItem = Item.of("밀", 1)
    }
    @Test
    @DisplayName("정상적인 아이템 수량 생성")
    fun createTest() {
        val itemSlot = ItemSlot.of(baseItem, 10)
        assertEquals(itemSlot, ItemSlot.of(baseItem, 10))
    }

    @Test
    @DisplayName("수량 0인 아이템 생성 가능")
    fun zeroItemTest() {
        val itemSlot = ItemSlot.of(baseItem, 0)
        assertEquals(0, itemSlot.quantity)
    }

    @Test
    @DisplayName("음수 item 생성 시 예외 발생")
    fun negativeitemTest() {
        assertThrows<IllegalArgumentException> {
            ItemSlot.of(baseItem, -1)
        }
    }
}
