package scripts.service

import scripts.domain.Item.ItemSlot
import scripts.domain.player.Player
import scripts.domain.quest.AssignedQuest
import scripts.domain.quest.TradeQuest
import scripts.domain.testObject.TestQuest

class QuestService {
    private val allQuests = TestQuest.quests
    private val assignedQuests: MutableList<AssignedQuest> = mutableListOf()

    fun fillerActive(inventory: List<ItemSlot>) {
        allQuests.forEach { quest: TradeQuest ->
            quest.tryToActivate(inventory)
        }
    }

    fun availableQuest(): List<TradeQuest> {
        return allQuests.filter { quest: TradeQuest -> quest.isActive() }
    }

    fun selectedQuest(inputNumber: Int): TradeQuest {
        return availableQuest()[inputNumber - 1]
    }

    fun progressOneDay(assignedQuest: AssignedQuest) {
        assignedQuests.add(assignedQuest)
        assignedQuests.forEach { assignedQuest -> assignedQuest.progressOneDay() }
    }

    fun completedQuest(player: Player) {
        val completedQuests = assignedQuests.filter { it.isCompleted() }
        completedQuests.forEach { aq ->
            aq.completed().applyTo(player)
            aq.resetToReady()
        }
    }
}
