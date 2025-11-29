package scripts.domain.quest

import scripts.domain.common.Gold
import scripts.domain.Item.ItemSlot
import scripts.domain.Item.ItemSlots
import scripts.domain.Item.Weight
import scripts.domain.common.ReputationPoint
import scripts.domain.reward.Rewards

class Quest private constructor(
    val city: City,
    val requiredItems: ItemSlots,
    val rewards: Rewards,
    val status: QuestStatus
) {
    fun activateWith(inventoryItems: List<ItemSlot>): Quest {
        if (canActivate(inventoryItems)) {
            return copyWithStatus(QuestStatus.ACTIVE)
        }
        return this
    }

    private fun canActivate(inventoryItems: List<ItemSlot>): Boolean =
        status.isInActive() && requiredItems.isFulfilledBy(inventoryItems)

    fun startProgress(): Quest {
        check(status.isActive()) { "활성화된 상태여야 합니다." }
        return copyWithStatus(QuestStatus.IN_PROGRESS)
    }

    fun complete(): Quest {
        check(status.isInProgress()) { "진행중인 상태여야 완료할 수 있습니다." }
        return copyWithStatus(QuestStatus.COMPLETED)
    }

    private fun copyWithStatus(newStatus: QuestStatus): Quest =
        Quest(city, requiredItems, rewards, newStatus)

    fun isActive(): Boolean = status.isActive()

    fun itemsToDeliver(): List<ItemSlot> = requiredItems.allItems()

    fun calculateTotalItemsWeight(): Weight = requiredItems.totalWeight()

    fun calculateTravelTime(maxCaravanSpeed: Int): Int {
        return city.calculateTravelTime(maxCaravanSpeed)
    }

    companion object {
        fun of(
            city: City, requiredItems: ItemSlots, gold: Gold, reputation: ReputationPoint, questStatus: QuestStatus
        ) = Quest(
            city, requiredItems, Rewards.of(gold, reputation), questStatus
        )

        fun of(
            city: City, requiredItems: ItemSlots, gold: Gold, reputation: ReputationPoint,
        ) = Quest(
            city, requiredItems, Rewards.of(gold, reputation), QuestStatus.INACTIVE
        )
    }
}
