package scripts.domain.quest

class ActiveQuests private constructor(
    private val quests: List<AssignedQuest>
) {
    fun assign(assignedQuest: AssignedQuest): ActiveQuests =
        ActiveQuests(quests + assignedQuest)

    fun progressOneDay(): ActiveQuests {
        quests.forEach { quest: AssignedQuest -> quest.checkComplete() }
        return ActiveQuests(quests.map { it.progressOneDay() })
    }

    fun allQuests(): List<AssignedQuest> {
        return quests.toList()
    }

    fun completedQuests(): List<AssignedQuest> {
        return quests.filter { it.isCompleted() }
    }

    fun removeCompleted(): ActiveQuests =
        ActiveQuests(quests.filterNot { it.isCompleted() })

    companion object {
        fun of(quests: List<AssignedQuest>): ActiveQuests {
            return ActiveQuests(quests)
        }

        fun empty(): ActiveQuests {
            return ActiveQuests(emptyList())
        }
    }
}
