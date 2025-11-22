package scripts.service

import scripts.domain.Item.ItemSlot
import scripts.domain.caravan.Caravan
import scripts.domain.player.Player
import scripts.domain.quest.AssignedQuest
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

    fun assignQuest(player: Player, quest: TradeQuest, caravan: Caravan): AssignedQuest {
        quest.startProgress()
        player.removeItems(quest.deliveryItems())
        player.updateCaravan(caravan.startTrip())

        val assignedQuest = AssignedQuest.of(quest, caravan)
        questRepository.save(assignedQuest)
        return assignedQuest
    }

    fun processQuestProgress(player: Player) {
        val completed = player.completedQuests()

        completed.forEach { assignedQuest ->
            val reward = assignedQuest.getReward()
            player.earnReward(reward)
        }
    }
}
