package scripts.domain.Item

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ItemSlotsTest {
    private lateinit var wheat: Item
    private lateinit var wood: Item
    private lateinit var spice: Item
    private lateinit var iron: Item

    @BeforeEach
    fun setUp() {
        wheat = Item.of("밀", 1)
        wood = Item.of("목재", 1)
        spice = Item.of("향료", 1)
        iron = Item.of("철", 1)
    }

    @Test
    @DisplayName("hasItems - 충분한 재고가 있으면 true")
    fun hasItemsTest() {
        val inventory = ItemSlots.of(
            listOf(
                ItemSlot.of(wheat, 10),
                ItemSlot.of(wood, 5)
            )
        )

        val request = listOf(
            ItemSlot.of(wheat, 5),
            ItemSlot.of(wood, 3)
        )

        assertTrue(inventory.hasItems(request))
    }

    @Test
    @DisplayName("isFulfilledBy - 충분한 재고가 있으면 true")
    fun isFulfilledByTest() {
        val request = ItemSlots.of(listOf(
            ItemSlot.of(wheat, 5),
            ItemSlot.of(wood, 3)
        ))

        val inventory = listOf(
            ItemSlot.of(wheat, 10),
            ItemSlot.of(wood, 5)
        )

        assertTrue(request.isFulfilledBy(inventory))
    }

    @Test
    @DisplayName("hasItems - 수량이 부족하면 false")
    fun failQuantity() {
        val inventory = ItemSlots.of(
            listOf(
                ItemSlot.of(wheat, 10)
            )
        )

        val request = listOf(
            ItemSlot.of(wheat, 15)
        )

        assertFalse(inventory.hasItems(request))
    }

    @Test
    @DisplayName("hasItems - 아이템 종류가 다르면 false")
    fun failNotFound() {
        val inventory = ItemSlots.of(
            listOf(
                ItemSlot.of(wood, 5)
            )
        )

        val request = listOf(
            ItemSlot.of(spice, 1)
        )

        assertFalse(inventory.hasItems(request))
    }

    @Test
    @DisplayName("hasItems - 요청 목록이 비어 있으면 false")
    fun failEmptyRequest() {
        val inventory = ItemSlots.of(
            listOf(
                ItemSlot.of(iron, 20)
            )
        )

        val request = emptyList<ItemSlot>()

        assertFalse(inventory.hasItems(request))
    }

    @Test
    @DisplayName("hasItems - 여러 요청 중 하나라도 부족하면 false")
    fun failPartialEnoughItems() {
        val inventory = ItemSlots.of(
            listOf(
                ItemSlot.of(wheat, 10),
                ItemSlot.of(wood, 5)
            )
        )

        val request = listOf(
            ItemSlot.of(wheat, 5),
            ItemSlot.of(wood, 10)
        )

        assertFalse(inventory.hasItems(request))
    }
}
