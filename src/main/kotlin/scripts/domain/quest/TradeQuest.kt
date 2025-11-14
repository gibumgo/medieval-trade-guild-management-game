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
) {

    fun isAvailableFor(player: Player): Boolean {
        return player.hasItems(requiredItems)
    }

    fun complete(player: Player) : Reward{
        require(isAvailableFor(player)) { "퀘스트 조건을 만족하지 않습니다." }
        player.removeItems(requiredItems)
        return Reward.of(gold, reputation)
    }
}