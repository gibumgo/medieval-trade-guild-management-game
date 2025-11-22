package scripts.domain.quest

import scripts.domain.common.Gold
import scripts.domain.Item.ItemSlot
import scripts.domain.Item.ItemSlots
import scripts.domain.common.ReputationPoint
import scripts.domain.reward.Reward
import scripts.domain.player.Player

class TradeQuest private constructor(
    val city: City,
    val requiredItems: ItemSlots,
    val reward: Reward,
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

    fun isActive(): Boolean = status.isActive()

    fun itemsToDeliver(): List<ItemSlot> = requiredItems.allItems()

    fun reward(): Reward = reward

    fun calculateDurationBy(speed: Int): Int = city.calculateTravelTime(speed)

    fun calculateMaxDuration(player: Player): Int {
        val speed = player.availableCaravanMaxSpeed()
        return city.calculateTravelTime(speed)
    }

    companion object {
        fun of(
            city: City, requiredItems: ItemSlots, gold: Gold, reputation: ReputationPoint, questStatus: QuestStatus
        ) = TradeQuest(
            city, requiredItems, Reward.ofQuestReward(gold, reputation), questStatus
        )

        fun of(
            city: City, requiredItems: ItemSlots, gold: Gold, reputation: ReputationPoint,
        ) = TradeQuest(
            city, requiredItems, Reward.ofQuestReward(gold, reputation), QuestStatus.INACTIVE
        )
    }
}
