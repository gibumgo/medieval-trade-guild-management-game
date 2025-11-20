package scripts.domain.supply

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import scripts.domain.player.Inventory
import scripts.domain.common.Capacity
import scripts.domain.common.Gold
import scripts.domain.Item.ItemSlot
import scripts.domain.Item.Item
import scripts.domain.Item.ItemSlots
import scripts.domain.common.ReputationPoint
import scripts.domain.player.Player
import scripts.domain.player.PlayerStatus
import scripts.domain.quest.ActiveQuests
import scripts.domain.reward.Reward
import kotlin.collections.listOf

class SupplyBoxTest {
    private lateinit var rewards: List<ItemSlot>
    private lateinit var box: SupplyBox
    private lateinit var player: Player

    @BeforeEach
    fun setUp() {
        rewards = listOf(
            ItemSlot.of(Item.of("밀", 1), 2)
        )

        box = SupplyBox.of(
            SupplyBoxType.BASIC,
            { Reward.ofItems(rewards) }
        )

        player = Player(
            playerStatus = PlayerStatus.of(Gold.of(1000), ReputationPoint.of(0)),
            inventory = Inventory(ItemSlots.of(listOf()), Capacity.of(0, 1000)),
            caravans = emptyList(),
            quests = ActiveQuests.empty()
        )
    }

    @Test
    @DisplayName("골드 충분 시 박스 구매 성공")
    fun purchaseSuccessTest() {
        val reward = box.purchaseBy(player.playerStatus)
        val wheat = Reward.ofItems(rewards)
        assertEquals(
            wheat,
            reward
        )
    }

    @Test
    @DisplayName("골드 부족 시 예외 발생")
    fun purchaseFailInsufficientGold() {
        val poorPlayer = Player(
            playerStatus = PlayerStatus.of(Gold.of(100), ReputationPoint.of(0)),
            inventory = Inventory(ItemSlots.of(listOf()), Capacity.of(0, 1000)),
            caravans = emptyList(),
            quests = ActiveQuests.empty()
        )

        val exception = assertThrows<IllegalArgumentException> {
            box.purchaseBy(poorPlayer.playerStatus)
        }

        assertEquals("구매 불가", exception.message)
    }
}