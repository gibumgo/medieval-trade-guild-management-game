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

    fun selectedQuest(inputNumber: Int): TradeQuest {
        require(inputNumber in 1..availableQuest().size) { "번호 선택이 범위를 벗어났습니다." }
        return availableQuest()[inputNumber - 1]
    }

    fun assignQuest(player: Player, quest: TradeQuest, caravan: Caravan): AssignedQuest {
        quest.startProgress()
        player.submitItems(quest.itemsToDeliver())

        val assignedQuest = AssignedQuest.of(quest, caravan)
        questRepository.save(assignedQuest)
        return assignedQuest
    }

    fun getInProgressQuests(): List<AssignedQuest> {
        return questRepository.findInProgress()
    }

    fun collectCompletedQuests(player: Player): List<AssignedQuest> {
        val completed = questRepository.findInProgress().filter { it.isCompleted() }
        completed.forEach { quest ->
            player.earnReward(quest.getReward())
        }
        questRepository.removeCompletedQuests(completed)
        return completed
    }

    fun rollDayForQuests() {
        val updatedQuests = questRepository.findInProgress()
            .map { it.progressOneDay() }
        updatedQuests.forEach { questRepository.save(it) }
    }
}
