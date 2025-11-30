package scripts.repository

import scripts.domain.quest.Quest
import scripts.domain.quest.QuestStatus

interface QuestRepository {
    fun findAll(): List<Quest>
    fun findByState(state: QuestStatus): List<Quest>
    fun update(quest: Quest)
    fun remove(quest: Quest)
}