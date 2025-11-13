package scripts.domain.supply

import scripts.domain.Gold
import scripts.domain.ReputationPoint

enum class SupplyBoxType(cost: Int, minReputationPoint: Int) {
    BASIC(500, 0),
    ADVANCED(1500, 10),
    ROYAL(3000, 30),
    LEGENDARY(5000, 50);

    val cost: Gold = Gold.Companion.of(cost);
    val minReputationPoint: ReputationPoint = ReputationPoint.Companion.of(minReputationPoint);


    fun canPurchase(playerGold: Gold, playerReputation: ReputationPoint): Boolean {
        val hasGold = playerGold.isAffordable(cost)
        val hasReputation = playerReputation.isAffordable(minReputationPoint)
        return hasGold && hasReputation
    }
}
