package scripts.repository

import kotlinx.serialization.json.Json
import scripts.domain.quest.Quest
import scripts.domain.quest.QuestDelivery
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

    private val inProgressQuests: MutableList<QuestDelivery> = mutableListOf()

    override fun findAll(): List<Quest> = allQuests

    override fun findActive(): List<Quest> = allQuests.filter { it.isActive() }

    override fun findInProgress(): List<QuestDelivery> = inProgressQuests.toList()

    override fun save(questDelivery: QuestDelivery) {
        inProgressQuests.removeIf { it.quest == questDelivery.quest }
        inProgressQuests.add(questDelivery)
    }

    override fun removeCompletedQuests(completed: List<QuestDelivery>) {
        inProgressQuests.removeAll(completed)
    }
}
