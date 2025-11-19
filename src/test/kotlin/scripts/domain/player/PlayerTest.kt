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
import scripts.domain.reward.Reward

class PlayerTest {

    private lateinit var player: Player

    @BeforeEach
    fun setUp() {
        val caravan1 = Caravan(
            name = "테스트1",
            leader = "A",
            speed = 3,
            maxCapacity = Weight.of(50),
            maintenanceCost = Gold.of(0),
            status = CaravanStatus.READY
        )

        val caravan2 = Caravan(
            name = "테스트2",
            leader = "B",
            speed = 5,
            maxCapacity = Weight.of(80),
            maintenanceCost = Gold.of(10),
            status = CaravanStatus.READY
        )

        player = Player(
            playerStatus = PlayerStatus.of(1000, 5),
            inventory = Inventory(ItemSlots.of(listOf()), Capacity.of(0, 1000)),
            caravans = listOf(caravan1, caravan2)
        )
    }

    @Test
    @DisplayName("골드 지불 시 playerStatus.gold 가 감소한다")
    fun payGoldTest() {
        player.pay(Gold.of(100))
        assertEquals(900, player.currentGold())
    }
}
