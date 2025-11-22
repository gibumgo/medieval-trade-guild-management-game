package scripts.repository

import kotlinx.serialization.json.Json
import scripts.domain.quest.AssignedQuest
import scripts.domain.quest.TradeQuest
import scripts.dto.TradeQuestDTO
import scripts.mapper.TradeQuestMapper
import java.io.File

class QuestRepositoryImpl : QuestRepository {
    private var activeQuests: List<AssignedQuest> = listOf()
    private val allQuests: MutableList<TradeQuest> by lazy {
        val quests: List<TradeQuestDTO> = Json.decodeFromString(
            File(
                "src/main/resources/gameData/quests.json"
            ).readText()
        )
        quests.map { TradeQuestMapper.fromDTO(it) }.toMutableList()
    }

    override fun findAll(): List<TradeQuest> {
        return allQuests
    }

    override fun findActive(): List<TradeQuest> {
        return allQuests.filter { it.isActive() }
    }

    override fun save(quest: TradeQuest) {
    }
}
