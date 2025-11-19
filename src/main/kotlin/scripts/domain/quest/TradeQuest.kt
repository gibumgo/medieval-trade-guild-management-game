package scripts.domain.quest

import scripts.domain.common.Gold
import scripts.domain.Item.ItemSlot
import scripts.domain.Item.ItemSlots
import scripts.domain.caravan.Caravan
import scripts.domain.common.ReputationPoint
import scripts.domain.reward.Reward
import scripts.domain.player.Player

class TradeQuest private constructor(
    val city: City,
    val requiredItems: ItemSlots,
    val reward: Reward,
    private var status: QuestStatus
) {
    fun activateWith(inventoryItems: List<ItemSlot>) {
        if (canActivate(inventoryItems)) {
            status = QuestStatus.ACTIVE
        }
    }

    private fun canActivate(inventoryItems: List<ItemSlot>): Boolean =
        status.isInActive() && requiredItems.hasItems(inventoryItems)


    fun canAssign(inventoryItems: List<ItemSlot>): Boolean =
        status.isActive() && requiredItems.hasItems(inventoryItems)

    fun isActive(): Boolean = status.isActive()

    fun assignTo(player: Player, caravan: Caravan): AssignedQuest {
        this.status = QuestStatus.IN_PROGRESS
        player.removeItems(requiredItems.allItems())
        return AssignedQuest.of(this, caravan)
    }

    fun assign() {
        require(status.isActive()) { "활성화된 상태여야 합니다." }
        status = QuestStatus.IN_PROGRESS
    }

    fun complete() {
        require(status.isInProgress()) { "진행중인 상태여야 완료할 수 있습니다." }
        status = QuestStatus.COMPLETED
    }

    fun reward(): Reward = reward

    fun tryToActivate(inventoryItems: List<ItemSlot>) {
        if (this.status.isInActive() && hasAllRequiredItems(inventoryItems)) {
            this.status = QuestStatus.ACTIVE
        }
    }

    private fun hasAllRequiredItems(inventoryItems: List<ItemSlot>): Boolean {
        return this.requiredItems.hasItems(inventoryItems) && inventoryItems.isNotEmpty()

    }

    fun calculateDurationBy(speed: Int): Int = city.calculateTravelTime(speed)

//    fun complete(): Reward {
//        return reward
//    }

    fun transitionToCompleted() {
        status = QuestStatus.COMPLETED
    }


    fun isCompleted(): Boolean {
        return this.status == QuestStatus.COMPLETED
    }

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
