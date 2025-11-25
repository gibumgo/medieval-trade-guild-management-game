package scripts.domain.quest

import scripts.domain.common.Gold
import scripts.domain.Item.ItemSlot
import scripts.domain.Item.ItemSlots
import scripts.domain.common.ReputationPoint
import scripts.domain.reward.Rewards

class TradeQuest private constructor(
    val city: City,
    val requiredItems: ItemSlots,
    val rewards: Rewards,
    var status: QuestStatus
) {
    fun activateWith(inventoryItems: List<ItemSlot>) {
        if (canActivate(inventoryItems)) {
            status = QuestStatus.ACTIVE
        }
    }

    private fun canActivate(inventoryItems: List<ItemSlot>): Boolean =
        status.isInActive() && requiredItems.hasItems(inventoryItems)

    fun startProgress() {
        require(status.isActive()) { "활성화된 상태여야 합니다." }
        status = QuestStatus.IN_PROGRESS
    }

    fun complete() {
        require(status.isInProgress()) { "진행중인 상태여야 완료할 수 있습니다." }
        status = QuestStatus.COMPLETED
    }

    fun totalRewardGold(): Gold = rewards.totalGold()

    fun totalRewardReputation()  = rewards.totalReputation()

    fun isActive(): Boolean = status.isActive()

    fun itemsToDeliver(): List<ItemSlot> = requiredItems.allItems()

    fun minTravelDays(maxCaravanSpeed: Int): Int {
        return city.calculateTravelTime(maxCaravanSpeed)
    }

    companion object {
        fun of(
            city: City, requiredItems: ItemSlots, gold: Gold, reputation: ReputationPoint, questStatus: QuestStatus
        ) = TradeQuest(
            city, requiredItems, Rewards.of(gold, reputation), questStatus
        )

        fun of(
            city: City, requiredItems: ItemSlots, gold: Gold, reputation: ReputationPoint,
        ) = TradeQuest(
            city, requiredItems, Rewards.of(gold, reputation), QuestStatus.INACTIVE
        )
    }
}
