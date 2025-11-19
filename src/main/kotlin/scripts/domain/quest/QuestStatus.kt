package scripts.domain.quest

enum class QuestStatus(val description: String) {
    INACTIVE("비활성화"),
    ACTIVE("활성화"),
    IN_PROGRESS("진행 중"),
    COMPLETED("완료");

    fun isInActive(): Boolean {
        return this == INACTIVE
    }

    fun isActive(): Boolean {
        return this == ACTIVE
    }

    fun isInProgress(): Boolean {
        return this == IN_PROGRESS
    }
}
