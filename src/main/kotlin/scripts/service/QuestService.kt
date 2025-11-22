package scripts.service

import scripts.domain.Item.ItemSlot
import scripts.domain.player.Player
import scripts.domain.quest.TradeQuest
import scripts.repository.QuestRepository

class QuestService(
    private val questRepository: QuestRepository
) {

    fun fillerActive(inventory: List<ItemSlot>) {
        questRepository.findAll().forEach { quest: TradeQuest ->
            quest.activateWith(inventory)
        }
    }

    fun availableQuest(): List<TradeQuest> = questRepository.findActive()


    fun selectedQuest(inputNumber: Int): TradeQuest =
        availableQuest()[inputNumber - 1]


    fun processQuestProgress(player: Player) {
        val completed = player.completedQuests()

        completed.forEach { assignedQuest ->
            val reward = assignedQuest.getReward()
            player.earnReward(reward)
        }
    }
}
