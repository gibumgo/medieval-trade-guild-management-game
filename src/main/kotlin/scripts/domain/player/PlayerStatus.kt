package scripts.domain.player

import scripts.domain.common.Gold
import scripts.domain.common.ReputationPoint

data class PlayerStatus private constructor(
    val gold: Gold,
    val reputationPoint: ReputationPoint
) {
    fun payGold(amount: Gold) = PlayerStatus(
        gold.minus(amount), reputationPoint
    )

    fun isAffordable(otherGold: Gold, otherPoint: ReputationPoint): Boolean =
        gold.isAffordable(otherGold) && reputationPoint.isAffordable(otherPoint)

    fun increaseGold(amount: Gold) = PlayerStatus(gold.plus(amount), reputationPoint)

    fun increaseReputation(amount: ReputationPoint) =
        PlayerStatus(gold, reputationPoint.increase(amount))

    companion object {
        fun of(gold: Gold, reputationPoint: ReputationPoint): PlayerStatus {
            return PlayerStatus(gold, reputationPoint)
        }

        fun of(gold: Int, reputationPoint: Int): PlayerStatus {
            return PlayerStatus(Gold.of(gold), ReputationPoint.of(reputationPoint))
        }
    }
}
