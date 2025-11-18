package scripts.domain.quest

import scripts.domain.common.City
import scripts.domain.common.Gold
import scripts.domain.Item.ItemSlot
import scripts.domain.Item.ItemSlots
import scripts.domain.caravan.Caravan
import scripts.domain.common.ReputationPoint
import scripts.domain.reward.Reward
import scripts.domain.player.Player

class TradeQuest(
    val city: City,
    val requiredItems: ItemSlots,
    val gold: Gold,
    val reputation: ReputationPoint,
    var status: QuestStatus
) {
    fun tryToActivate(inventoryItems: List<ItemSlot>) {
        if (this.status.isInActive() && hasAllRequiredItems(inventoryItems)) {
            status = QuestStatus.ACTIVE
        }
    }

    private fun hasAllRequiredItems(inventoryItems: List<ItemSlot>): Boolean =
        this.requiredItems.hasItems(inventoryItems)

    fun assignTo(player: Player, caravan: Caravan): AssignedQuest {
        this.status = QuestStatus.IN_PROGRESS
        player.removeItems(requiredItems.items())
        return AssignedQuest.of(this, caravan)
    }

    fun calculateDurationBy(speed: Int): Int = city.calculateTravelTime(speed)

    fun complete(): Reward {
        return Reward.ofQuestReward(gold, reputation)
    }

    fun transitionToCompleted() {
        status = QuestStatus.COMPLETED
    }

    fun isActive(): Boolean {
        return this.status == QuestStatus.ACTIVE
    }

    fun isCompleted(): Boolean {
        return this.status == QuestStatus.COMPLETED
    }

    fun calculateMaxDuration(player: Player): Int {
        val speed = player.availableCaravanMaxSpeed()
        return city.calculateTravelTime(speed)
    }
}
