package scripts.repository

import scripts.domain.quest.TradeQuest

interface QuestRepository {
    fun findAll(): List<TradeQuest>
    fun findActive(): List<TradeQuest>
    fun save(quest: TradeQuest)
}
