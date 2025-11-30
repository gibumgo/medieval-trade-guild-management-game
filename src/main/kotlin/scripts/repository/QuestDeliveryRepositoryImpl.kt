package scripts.repository

import scripts.domain.quest.QuestDelivery

class QuestDeliveryRepositoryImpl : QuestDeliveryRepository {
    private val deliveries: MutableList<QuestDelivery> = mutableListOf()

    override fun save(delivery: QuestDelivery) {
        remove(delivery)
        deliveries.add(delivery)
    }

    override fun findAll(): List<QuestDelivery> = deliveries.toList()

    override fun findComplete(): List<QuestDelivery> =
        deliveries.filter { it.isCompletedProgress() }.toList()

    override fun remove(delivery: QuestDelivery) {
        deliveries.removeIf { it.quest.city.name == delivery.quest.city.name }
    }
}
