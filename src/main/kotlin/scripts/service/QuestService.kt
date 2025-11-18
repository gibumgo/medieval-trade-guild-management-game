package scripts.service

import scripts.domain.Item.ItemSlot
import scripts.domain.quest.TradeQuest
import scripts.domain.testObject.TestQuest

class QuestService {
    private val allQuests = TestQuest.quests

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
}
