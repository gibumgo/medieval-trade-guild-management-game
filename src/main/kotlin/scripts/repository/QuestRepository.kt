package scripts.repository

import scripts.domain.quest.AssignedQuest
import scripts.domain.quest.TradeQuest

interface QuestRepository {
    fun findAll(): List<TradeQuest>
    fun findActive(): List<TradeQuest>
    fun findInProgress(): List<AssignedQuest>
    fun save(assignedQuest: AssignedQuest)
    fun removeCompletedQuests(completed: List<AssignedQuest>)
}
