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
        // 플레이어 상태 서비스로 이동
        player.submitItems(quest.itemsToDeliver())
        val progressQuest = quest.startProgress()
        questRepository.update(progressQuest)
        return QuestDelivery.of(progressQuest, caravan)
    }

    fun getInProgressQuests(): List<QuestDelivery> = questDeliveryRepository.findAll()

    fun collectCompletedQuests(player: Player): List<QuestDelivery> {
        val completedQuest = questDeliveryRepository.findComplete()

        completedQuest.forEach { quest ->
            player.earnReward(quest.quest.rewards)
            questRepository.update(quest.quest)
            questDeliveryRepository.remove(quest)
        }
        return completedQuest
    }

    fun rollDayForQuests() {
        val deliveries = questDeliveryRepository.findAll()
        deliveries.forEach { delivery ->
            val progressed = delivery.progressOneDay()
            if (progressed.isCompletedProgress()) {
                questDeliveryRepository.save(progressed.completedQuest())
            }
            questDeliveryRepository.save(progressed)
        }
    }
}
