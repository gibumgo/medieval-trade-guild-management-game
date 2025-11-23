package scripts.service

import scripts.domain.player.Inventory
import scripts.domain.caravan.Caravan
import scripts.domain.caravan.CaravanStatus
import scripts.domain.common.Capacity
import scripts.domain.common.Gold
import scripts.domain.Item.ItemSlot
import scripts.domain.Item.ItemSlots
import scripts.domain.Item.Weight
import scripts.domain.player.Player
import scripts.domain.player.PlayerStatus
import scripts.domain.reward.Reward
import scripts.domain.reward.Rewards
import scripts.domain.supply.SupplyBox

class PlayerStatusService() {
    private val player = initPlayer()
    fun player(): Player {
        return player
    }

    fun status(): PlayerStatus {
        return player.playerStatus
    }

    private fun initPlayer(): Player {
        return Player(
            PlayerStatus.of(10000, 0),
            Inventory(ItemSlots.of(listOf()), Capacity.of(0, 1000)),
        )
    }

    fun receiveSupplyBox(supplyBox: SupplyBox): Rewards {
        validPurchase(supplyBox)
        val rewards = supplyBox.purchaseBy()
        player.pay(supplyBox.price())
        player.earnReward(rewards)
        return rewards
    }

    private fun validPurchase(supplyBox: SupplyBox) {
        require(
            player.playerStatus.isAffordable(
                supplyBox.price(),
                supplyBox.minReputationPoint()
            )
        ) { " 구매 불가: 골드 또는 명성치 부족" }
    }

    fun currentInventory(): List<ItemSlot> {
        return player.allItems()
    }
}
