package scripts.service

import scripts.domain.Item.Inventory
import scripts.domain.caravan.Caravan
import scripts.domain.caravan.CaravanStatus
import scripts.domain.common.Capacity
import scripts.domain.common.Gold
import scripts.domain.Item.ItemSlot
import scripts.domain.common.ReputationPoint
import scripts.domain.common.Weight
import scripts.domain.player.Player
import scripts.domain.player.PlayerStatus
import scripts.domain.reward.Reward
import scripts.domain.supply.SupplyBox


class PlayerStatusService() {
    private val player = initPlayer()
    fun status(): Player {
        return player
    }

    private fun initPlayer(): Player {
        val caravan1 = Caravan(
            name = "로반의 행상대",
            leader = "로반",
            speed = 3,
            maxCapacity = Weight.of(100),
            maintenanceCost = Gold.of(0),
            status = CaravanStatus.READY,
        )

        val caravan2 = Caravan(
            name = "리아의 행상대",
            leader = "리아",
            speed = 2,
            maxCapacity = Weight.of(80),
            maintenanceCost = Gold.of(15),
            status = CaravanStatus.READY
        )
        return Player(
            PlayerStatus.of(10000,0),
            Capacity.of(0, 100),
            Inventory(mutableListOf()),
            mutableListOf(caravan1, caravan2),
        )
    }

    fun receiveSupplyBox(supplyBox: SupplyBox): Reward {
        val reward = supplyBox.purchaseBy(player)
        player.earnReward(reward)
        return reward
    }

    fun currentInventory(): List<ItemSlot> {
        return player.allItems()
    }

    fun availableCaravans(): List<Caravan> {
        return player.availableCaravans()
    }
}
