package scripts.domain

enum class SupplyBoxType(cost: Int, minReputationPoint: Int) {
    BASIC(500, 0),
    ADVANCED(1500, 10),
    ROYAL(3000, 30),
    LEGENDARY(5000, 50);

    val cost: Gold = Gold.of(cost);
    val minReputationPoint: ReputationPoint = ReputationPoint.of(minReputationPoint);

}
