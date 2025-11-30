package scripts.repository

import kotlinx.serialization.json.Json
import scripts.domain.quest.Quest
import scripts.domain.quest.QuestStatus
import scripts.dto.QuestDTO
import scripts.mapper.QuestMapper
import java.io.File

class QuestRepositoryImpl : QuestRepository {
    private val allQuests: MutableList<Quest> by lazy {
        val quests: List<QuestDTO> = Json.decodeFromString(
            File(
                "src/main/resources/gameData/quests.json"
            ).readText()
        )
        quests.map { QuestMapper.fromDTO(it) }.toMutableList()
    }

    override fun findAll(): List<Quest> = allQuests.toList()

    override fun findByState(state: QuestStatus): List<Quest> {
        return allQuests.filter { quest -> quest.status == state }.toList()
    }

    override fun update(quest: Quest) {
        allQuests.removeIf { it.city.name == quest.city.name }
        allQuests.add(quest)
    }

    override fun remove(quest: Quest) {
        allQuests.removeIf { it.city.name == quest.city.name }
    }
}