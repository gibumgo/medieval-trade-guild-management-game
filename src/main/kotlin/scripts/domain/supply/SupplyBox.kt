package scripts.domain.supply

import scripts.domain.reward.Reward
import scripts.domain.player.Player
import scripts.domain.reward.RewardGenerator

class SupplyBox private constructor(
    private val type: SupplyBoxType,
    private val rewardGenerator: RewardGenerator
) {

    fun purchaseBy(player: Player): Reward {
        validPurchase(player)
        return rewardGenerator.generate()
    }

    private fun validPurchase(player: Player) {
        require(player.canAffordSupplyBox(type)) { "구매 불가" }
    }

    companion object {
        fun of(type: SupplyBoxType, rewardGenerator: RewardGenerator): SupplyBox {
            return SupplyBox(type, rewardGenerator)
        }
    }
}
