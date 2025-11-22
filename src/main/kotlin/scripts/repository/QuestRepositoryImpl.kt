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
        activeQuests.removeIf { it.quest == assignedQuest.quest }
        activeQuests.add(assignedQuest)
    }

    fun removeCompletedQuests() {
        val completed = activeQuests.filter { it.isCompleted() }
        activeQuests.removeAll(completed)
    }
}
