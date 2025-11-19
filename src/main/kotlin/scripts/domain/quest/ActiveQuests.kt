package scripts.domain.quest

class ActiveQuests private constructor(
    private val quests: List<AssignedQuest>
) {
    fun assign(assignedQuest: AssignedQuest): ActiveQuests =
        ActiveQuests(quests + assignedQuest)

    fun progressOneDay(): ActiveQuests =
        ActiveQuests(quests.map { it.progressOneDay() })


    companion object {
        fun of(quests: List<AssignedQuest>): ActiveQuests {
            return ActiveQuests(quests)
        }

        fun empty(): ActiveQuests {
            return ActiveQuests(emptyList())
        }
    }
}
