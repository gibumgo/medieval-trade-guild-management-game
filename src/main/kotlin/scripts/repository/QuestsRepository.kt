package scripts.repository

import scripts.domain.quest.Quest
import scripts.domain.quest.QuestDelivery

interface QuestsRepository {
    fun findAll(): List<Quest>
    fun findActive(): List<Quest>
    fun findInProgress(): List<QuestDelivery>
    fun save(QuestDelivery: QuestDelivery)
    fun removeCompletedQuests(completed: List<QuestDelivery>)
}
