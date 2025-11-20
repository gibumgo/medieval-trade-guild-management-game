package scripts.service

import scripts.domain.reward.BasicRewardGenerator
import scripts.domain.supply.SupplyBox
import scripts.domain.supply.SupplyBoxType

private const val MIN_INDEX_NUMBER = 1

class SupplyService {
    fun allSupplyType(): List<SupplyBoxType> {
        return SupplyBoxType.entries
    }

    fun openSupplyBox(inputIndex: Int): SupplyBox {
        require(inputIndex in MIN_INDEX_NUMBER..SupplyBoxType.values().size) { "번호 선택이 범위를 벗어났습니다." }
        val type = SupplyBoxType.from(inputIndex)
        return SupplyBox.of(type, generators[type]!!)
    }

    private val generators = mapOf(
        SupplyBoxType.BASIC to BasicRewardGenerator(),
        SupplyBoxType.ADVANCED to BasicRewardGenerator(),
        SupplyBoxType.ROYAL to BasicRewardGenerator(),
        SupplyBoxType.LEGENDARY to BasicRewardGenerator(),
    )
}
