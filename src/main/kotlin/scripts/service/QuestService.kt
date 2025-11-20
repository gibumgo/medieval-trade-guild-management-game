package scripts.service

import scripts.domain.Item.ItemSlot
import scripts.domain.player.Player
import scripts.domain.quest.TradeQuest
import scripts.domain.testObject.TestQuest

class QuestService {
    private val allQuests = TestQuest.quests

    fun fillerActive(inventory: List<ItemSlot>) {
        allQuests.forEach { quest: TradeQuest ->
            quest.activateWith(inventory)
        }
    }

    fun availableQuest(): List<TradeQuest> =
        allQuests.filter { quest: TradeQuest -> quest.isActive() }


    fun selectedQuest(inputNumber: Int): TradeQuest =
        availableQuest()[inputNumber - 1]


    fun processQuestProgress(player: Player) {
        player.progressDay()
        val completed = player.completedQuests()

        completed.forEach { assignedQuest ->
            val reward = assignedQuest.getReward()
            player.earnReward(reward)
        }
    }
}
