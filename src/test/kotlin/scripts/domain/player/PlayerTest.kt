package scripts.domain.player

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import scripts.domain.Item.Item
import scripts.domain.Item.ItemSlot
import scripts.domain.Item.ItemSlots
import scripts.domain.Item.Weight
import scripts.domain.caravan.Caravan
import scripts.domain.caravan.CaravanStatus
import scripts.domain.common.Capacity
import scripts.domain.common.Gold
import scripts.domain.common.ReputationPoint
import scripts.domain.reward.Rewards

class PlayerTest {

    private lateinit var player: Player

    @BeforeEach
    fun setUp() {
        player = Player(
            playerStatus = PlayerStatus.of(1000, 5),
            inventory = Inventory(ItemSlots.of(listOf()), Capacity.of(0, 1000)),
        )
    }

    @Test
    @DisplayName("골드 지불 시 playerStatus.gold 가 감소한다")
    fun payGoldTest() {
        player.pay(Gold.of(100))
        assertEquals(900, player.currentGold())
    }

    @Test
    @DisplayName("보상 획득 시 Gold, Reputation 이 증가하고 아이템이 인벤토리에 추가된다")
    fun earnRewardTest() {
        val item = Item.of("밀", 1)
        val rewards = Rewards.of(
            gold = Gold.of(200),
            reputation = ReputationPoint.of(3),
            items = listOf(ItemSlot.of(item, 5))
        )

        player.earnReward(rewards)

        assertEquals(Gold.of(1200), player.playerStatus.gold)
        assertEquals(ReputationPoint.of(8), player.playerStatus.reputationPoint)

        val items = player.inventory.allItems()
        assertEquals(1, items.size)
        assertEquals(ItemSlot.of(item, 5), items.first())
    }
}
