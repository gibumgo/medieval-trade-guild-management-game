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

    private val inProgressQuests: MutableList<AssignedQuest> = mutableListOf()

    override fun findAll(): List<TradeQuest> = allQuests

    override fun findActive(): List<TradeQuest> = allQuests.filter { it.isActive() }

    override fun findInProgress(): List<AssignedQuest> = inProgressQuests.toList()

    override fun save(assignedQuest: AssignedQuest) {
        inProgressQuests.removeIf { it.quest == assignedQuest.quest }
        inProgressQuests.add(assignedQuest)
    }

    override fun removeCompletedQuests(completed: List<AssignedQuest>) {
        inProgressQuests.removeAll(completed)
    }
}
