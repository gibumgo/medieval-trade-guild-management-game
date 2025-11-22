package scripts.repository

import kotlinx.serialization.json.Json
import scripts.domain.quest.AssignedQuest
import scripts.domain.quest.TradeQuest
import scripts.dto.TradeQuestDTO
import scripts.mapper.TradeQuestMapper
import java.io.File

class QuestRepositoryImpl : QuestRepository {
    private val allQuests: MutableList<TradeQuest> by lazy {
        val quests: List<TradeQuestDTO> = Json.decodeFromString(
            File(
                "src/main/resources/gameData/quests.json"
            ).readText()
        )
        quests.map { TradeQuestMapper.fromDTO(it) }.toMutableList()
    }

    private val activeQuests: MutableList<AssignedQuest> = mutableListOf()

    override fun findAll(): List<TradeQuest> {
        return allQuests
    }

    override fun findActive(): List<TradeQuest> {
        return allQuests.filter { it.isActive() }
    }

    override fun save(assignedQuest: AssignedQuest) {
        findIndexOfQuest(assignedQuest)
            .takeIf { it >= 0 }
            ?.let { updateAssignedQuest(it, assignedQuest) }
            ?: addAssignedQuest(assignedQuest)
    }

    private fun findIndexOfQuest(assignedQuest: AssignedQuest): Int {
        return activeQuests.indexOfFirst { it == assignedQuest }
    }

    private fun updateAssignedQuest(index: Int, assignedQuest: AssignedQuest) {
        activeQuests[index] = assignedQuest
    }

    private fun addAssignedQuest(assignedQuest: AssignedQuest) {
        activeQuests.add(assignedQuest)
    }

    fun removeCompletedQuests() {
        val completed = activeQuests.filter { it.isCompleted() }
        activeQuests.removeAll(completed)
    }
}
