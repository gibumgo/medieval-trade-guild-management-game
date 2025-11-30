package scripts.service

import scripts.domain.Item.ItemSlot
import scripts.domain.caravan.Caravan
import scripts.domain.player.Player
import scripts.domain.quest.Quest
import scripts.domain.quest.QuestDelivery
import scripts.repository.QuestsRepository

class QuestService(
    private val questRepository: QuestsRepository
) {

    fun fillerActive(inventory: List<ItemSlot>) {
        questRepository.findAll().forEach { quest: Quest ->
            quest.activateWith(inventory)
        }
    }

    fun availableQuest(): List<Quest> = questRepository.findActive()

    fun selectedQuest(inputNumber: Int): Quest {
        require(inputNumber in 1..availableQuest().size) { "번호 선택이 범위를 벗어났습니다." }
        return availableQuest()[inputNumber - 1]
    }

    fun assignQuest(player: Player, quest: Quest, caravan: Caravan): QuestDelivery {
        quest.startProgress()
        player.submitItems(quest.itemsToDeliver())

        val assignedQuest = QuestDelivery.of(quest, caravan)
        questRepository.save(assignedQuest)
        return assignedQuest
    }

    fun getInProgressQuests(): List<QuestDelivery> {
        return questRepository.findInProgress()
    }

    fun collectCompletedQuests(player: Player): List<QuestDelivery> {
        val completed = questRepository.findInProgress().filter { it.quest.status.isInProgress() }
        completed.forEach { quest ->
            player.earnReward(quest.quest.rewards)
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
