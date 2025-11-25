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

    private object QuestFixture {
        fun testQuest(
            city: City = City("테스트 도시", 6),
            requiredItems: ItemSlots = ItemSlots.of(emptyList()),
            gold: Gold = Gold.of(100),
            reputation: ReputationPoint = ReputationPoint.of(1),
            status: QuestStatus = QuestStatus.INACTIVE,
        ): TradeQuest {
            return TradeQuest.of(
                city = city,
                requiredItems = requiredItems,
                gold = gold,
                reputation = reputation,
                questStatus = status
            )
        }
    }

    @BeforeEach
    fun setUp() {
        wheat = Item.of("밀", 1)
        wood = Item.of("목재", 1)
        wheat10 = listOf(ItemSlot.of(wheat, 10))
        wood5 = listOf(ItemSlot.of(wood, 5))
    }

    @Test
    @DisplayName("퀘스트 생성 시 기본 상태는 INACTIVE")
    fun createQuest() {
        val quest = QuestFixture.testQuest()
        assertEquals(QuestStatus.INACTIVE, quest.status)
    }

    @Test
    @DisplayName("인벤토리에 필요한 아이템이 충분하면 퀘스트 활성화")
    fun activate() {
        val quest = QuestFixture.testQuest(
            requiredItems = ItemSlots.of(wheat10)
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
        val quest = QuestFixture.testQuest(
            requiredItems = ItemSlots.of(wheat10)
        )

        val inventory = listOf(ItemSlot.of(wheat, 8))
        quest.activateWith(inventory)

        assertFalse(quest.isActive())
        assertEquals(QuestStatus.INACTIVE, quest.status)
    }

    @Test
    @DisplayName("비활성 상태에서 startProgress 호출 시 예외 발생")
    fun startProgressFails() {
        val quest = QuestFixture.testQuest(
            status = QuestStatus.INACTIVE
        )
        val exception = assertThrows<IllegalArgumentException> {
            quest.startProgress()
        }
        assertEquals("활성화된 상태여야 합니다.", exception.message)
    }

    @Test
    @DisplayName("활성화된 상태에서 startProgress 호출 시 IN_PROGRESS 로 변경")
    fun startProgress() {
        val quest = QuestFixture.testQuest(
            status = QuestStatus.ACTIVE
        )

        quest.startProgress()
        assertEquals(QuestStatus.IN_PROGRESS, quest.status)
    }

    @Test
    @DisplayName("진행중이 아닐 때 complete 호출 시 예외 발생")
    fun completeFails() {
        val quest = QuestFixture.testQuest(
            status = QuestStatus.ACTIVE
        )

        val exception = assertThrows<IllegalArgumentException> {
            quest.complete()
        }
        assertEquals("진행중인 상태여야 완료할 수 있습니다.", exception.message)
    }

    @Test
    @DisplayName("진행중일 때 complete 호출 시 COMPLETED 로 변경")
    fun complete() {
        val quest = QuestFixture.testQuest(
            status = QuestStatus.IN_PROGRESS
        )
        quest.complete()
        assertEquals(QuestStatus.COMPLETED, quest.status)
    }
}
