package scripts.domain.supply

import scripts.domain.reward.Reward
import scripts.domain.player.PlayerStatus
import scripts.domain.reward.RewardGenerator

class SupplyBox private constructor(
    val type: SupplyBoxType,
    val rewardGenerator: RewardGenerator
) {
    fun purchaseBy(status: PlayerStatus): Reward {
        validBuy(status)
        return rewardGenerator.generate()
    }

    private fun validBuy(status: PlayerStatus) {
        require(this.type.canPurchase(status)) { "구매 불가" }
    }

    companion object {
        fun of(type: SupplyBoxType, rewardGenerator: RewardGenerator): SupplyBox {
            return SupplyBox(type, rewardGenerator)
        }
    }
}
