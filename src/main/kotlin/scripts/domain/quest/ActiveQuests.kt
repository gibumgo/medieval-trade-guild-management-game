package scripts.domain.quest

import scripts.domain.caravan.Caravan

class ActiveQuests private constructor(
    private val quests: List<AssignedQuest>
) {
    fun assign(assignedQuest: AssignedQuest): ActiveQuests =
        ActiveQuests(quests + assignedQuest)

    fun progressOneDay(): ActiveQuests {
        val endDayQuests = ActiveQuests(quests.map { it.progressOneDay() })
        return endDayQuests
    }

    fun allQuests(): List<AssignedQuest> {
        return quests.toList()
    }

    fun completedQuests(): List<AssignedQuest> {
        return quests.filter { it.isCompleted() }
    }

    fun removeCompleted(): Pair<ActiveQuests, List<Caravan>> {
        val completedQuests = completedQuests()

        val readyCaravans = completedQuests.map { it.completeCaravan() }
        val activeQuests = ActiveQuests(quests.filterNot { it.isCompleted() })
        return Pair(activeQuests, readyCaravans)
    }

    companion object {
        fun of(quests: List<AssignedQuest>): ActiveQuests {
            return ActiveQuests(quests)
        }

        fun empty(): ActiveQuests {
            return ActiveQuests(emptyList())
        }
    }
}
