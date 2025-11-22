package scripts.domain.player

import scripts.domain.common.Gold
import scripts.domain.common.ReputationPoint
import scripts.domain.reward.Reward

data class PlayerStatus private constructor(
    val gold: Gold,
    val reputationPoint: ReputationPoint
) {
    fun payGold(amount: Gold) = PlayerStatus(
        gold.minus(amount), reputationPoint
    )

    fun isAffordable(otherGold: Gold, otherPoint: ReputationPoint): Boolean =
        gold.isAffordable(otherGold) && reputationPoint.isAffordable(otherPoint)

    fun increase(otherGold: Gold, otherPoint: ReputationPoint) =
        PlayerStatus(gold.plus(otherGold), reputationPoint.increase(otherPoint))

    fun apply(reward: Reward): PlayerStatus = reward.applyTo(this)

    companion object {
        fun of(gold: Gold, reputationPoint: ReputationPoint): PlayerStatus {
            return PlayerStatus(gold, reputationPoint)
        }

        fun of(gold: Int, reputationPoint: Int): PlayerStatus {
            return PlayerStatus(Gold.of(gold), ReputationPoint.of(reputationPoint))
        }
    }
}
