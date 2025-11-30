package scripts.repository

import scripts.domain.quest.QuestDelivery

interface QuestDeliveryRepository {
    fun save(delivery: QuestDelivery)
    fun findAll(): List<QuestDelivery>
    fun findComplete(): List<QuestDelivery>
    fun remove(delivery: QuestDelivery)
}
