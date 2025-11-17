package scripts.domain.quest

import scripts.domain.common.City
import scripts.domain.common.Gold
import scripts.domain.Inventory.InventoryItem
import scripts.domain.caravan.Caravan
import scripts.domain.common.ReputationPoint
import scripts.domain.common.Reward
import scripts.domain.player.Player

class TradeQuest(
    val city: City,
    val requiredItems: List<InventoryItem>,
    val gold: Gold,
    val reputation: ReputationPoint,
    var status: QuestStatus
) {

    fun isAvailableFor(player: Player): Boolean {
        status = QuestStatus.ACTIVE
        return player.hasItems(requiredItems)
    }

    fun assignTo(player: Player, caravan: Caravan): AssignedQuest {
        this.status = QuestStatus.IN_PROGRESS
        player.removeItems(requiredItems)
        return AssignedQuest.of(this, caravan)
    }

    fun calculateDurationBy(speed: Int): Int = city.calculateTravelTime(speed)

    fun complete(): Reward {
        return Reward.ofQuestReward(gold, reputation)
    }

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
}