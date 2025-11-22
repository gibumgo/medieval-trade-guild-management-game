package scripts.domain.supply

import scripts.domain.common.Gold
import scripts.domain.common.ReputationPoint

enum class SupplyBoxType(
    name: String,
    cost: Int,
    minReputationPoint: Int
) {
    BASIC("기본 보급 상자", 500, 0),
    ADVANCED("고급 보급 상자", 1500, 10),
    ROYAL("왕실 보급 상자", 3000, 30),
    LEGENDARY("전설 보급 상자", 5000, 50);

    val cost: Gold = Gold.of(cost);
    val minReputationPoint: ReputationPoint = ReputationPoint.of(minReputationPoint);

    companion object {
        private const val SELECTION_START_NUMBER = 1
        private const val INDEX_OFFSET = 1

        fun from(selectNumber: Int): SupplyBoxType {
            val SupplyBoxTypes = entries.toList()
            validNumber(selectNumber, SupplyBoxTypes)
            return SupplyBoxTypes[selectNumber - INDEX_OFFSET]
        }

        private fun validNumber(selectNumber: Int, SupplyBoxTypes: List<SupplyBoxType>) {
            require(selectNumber in SELECTION_START_NUMBER..SupplyBoxTypes.size) {
                "유효하지 않은 선택 번호입니다: $selectNumber"
            }
        }
    }
}
