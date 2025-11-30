package scripts.service

import scripts.domain.Item.ItemSlot
import scripts.domain.caravan.Caravan
import scripts.domain.player.Player
import scripts.domain.quest.Quest
import scripts.domain.quest.QuestDelivery
import scripts.domain.quest.QuestStatus
import scripts.repository.QuestDeliveryRepository
import scripts.repository.QuestRepository

class QuestService(
    private val questRepository: QuestRepository,
    private val questDeliveryRepository: QuestDeliveryRepository,
) {
    fun fillerActive(inventory: List<ItemSlot>) {
        questRepository.findByState(QuestStatus.INACTIVE).forEach { quest: Quest ->
            val activeQuest = quest.activateWith(inventory)
            questRepository.update(activeQuest)
        }
    }

    fun availableQuest(): List<Quest> = questRepository.findByState(QuestStatus.ACTIVE)

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
