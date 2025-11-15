package scripts.domain.quest

import scripts.domain.common.City
import scripts.domain.common.Gold
import scripts.domain.Inventory.InventoryItem
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

    fun complete(player: Player) : Reward{
        require(isAvailableFor(player)) { "퀘스트 조건을 만족하지 않습니다." }
        status = QuestStatus.COMPLETED
        player.removeItems(requiredItems)
        return Reward.of(gold, reputation)
    }

    fun calculateDuration(player: Player): Int {
        val speed = player.availableCaravanMaxSpeed()
        return city.calculateTravelTime(speed)
    }
}