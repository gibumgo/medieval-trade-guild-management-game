package scripts.service

import scripts.domain.Item.ItemSlot
import scripts.domain.player.Player
import scripts.domain.quest.ActiveQuests
import scripts.domain.quest.AssignedQuest
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


    fun progressOneDay(assignedQuest: AssignedQuest) {
        assignedQuests.add(assignedQuest)
        assignedQuests.forEach { assignedQuest -> assignedQuest.progressOneDay() }
    }

    fun completedQuest(player: Player) {
        val completedQuests = assignedQuests.filter { it.isCompleted() }

        completedQuests.forEach { completedQuest ->
            player.earnReward(completedQuest.completed())
            completedQuest.resetToReady()
        }
    }
}
