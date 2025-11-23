package scripts.domain.supply

import scripts.domain.common.Gold
import scripts.domain.common.ReputationPoint
import scripts.domain.reward.RewardGenerator
import scripts.domain.reward.Rewards

class SupplyBox private constructor(
    val type: SupplyBoxType,
    val rewardGenerator: RewardGenerator
) {
    fun purchaseBy(): Rewards {
        return rewardGenerator.generate()
    }

    fun price(): Gold = type.cost

    fun minReputationPoint(): ReputationPoint = type.minReputationPoint

    companion object {
        fun of(type: SupplyBoxType, rewardGenerator: RewardGenerator): SupplyBox {
            return SupplyBox(type, rewardGenerator)
        }
    }
}
