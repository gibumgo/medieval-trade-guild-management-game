package scripts.domain.quest

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import scripts.domain.common.Gold
import scripts.domain.common.ReputationPoint
import scripts.domain.Item.Item
import scripts.domain.Item.ItemSlot
import scripts.domain.Item.ItemSlots

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TradeQuestTest {

    private lateinit var wheat: Item
    private lateinit var wood: Item
    private lateinit var wheat10: List<ItemSlot>
    private lateinit var wood5: List<ItemSlot>
    private lateinit var northCity: City

    @BeforeEach
    fun setUp() {
        wheat = Item.of("밀", 1)
        wood = Item.of("목재", 1)
        wheat10 = listOf(ItemSlot.of(wheat, 10))
        wood5 = listOf(ItemSlot.of(wood, 5))
        northCity = City("북부 도시", distance = 6)
    }

    @Test
    @DisplayName("퀘스트 생성 시 기본 상태는 INACTIVE")
    fun createQuest() {
        val quest = TradeQuest.of(
            city = northCity,
            requiredItems = ItemSlots.of(wheat10),
            gold = Gold.of(100),
            reputation = ReputationPoint.of(2)
        )
        assertEquals(QuestStatus.INACTIVE, quest.status)
    }

    @Test
    @DisplayName("인벤토리에 필요한 아이템이 충분하면 퀘스트 활성화")
    fun activate() {
        val quest = TradeQuest.of(
            city = northCity,
            requiredItems = ItemSlots.of(wheat10),
            gold = Gold.of(120),
            reputation = ReputationPoint.of(3)
        )

        val inventory = listOf(
            ItemSlot.of(wheat, 10)
        )
        quest.activateWith(inventory)

        assertTrue(quest.isActive())
        assertEquals(QuestStatus.ACTIVE, quest.status)
    }

    @Test
    @DisplayName("아이템이 부족하면 퀘스트 활성화되지 않음")
    fun notActivate() {
        val quest = TradeQuest.of(
            city = northCity,
            requiredItems = ItemSlots.of(listOf()),
            gold = Gold.of(120),
            reputation = ReputationPoint.of(3)
        )

        val inventory = listOf(ItemSlot.of(wheat, 8))
        quest.activateWith(inventory)

        assertFalse(quest.isActive())
        assertEquals(QuestStatus.INACTIVE, quest.status)
    }
}
