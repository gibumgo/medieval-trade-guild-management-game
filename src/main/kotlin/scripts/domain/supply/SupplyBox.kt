package scripts.domain.supply

import scripts.domain.common.Gold
import scripts.domain.reward.Reward
import scripts.domain.reward.RewardGenerator

class SupplyBox private constructor(
    val type: SupplyBoxType,
    val rewardGenerator: RewardGenerator
) {
    fun purchaseBy(): Reward {
        return rewardGenerator.generate()
    }

    fun price(): Gold {
        return type.cost
    }

    companion object {
        fun of(type: SupplyBoxType, rewardGenerator: RewardGenerator): SupplyBox {
            return SupplyBox(type, rewardGenerator)
        }
    }
}
