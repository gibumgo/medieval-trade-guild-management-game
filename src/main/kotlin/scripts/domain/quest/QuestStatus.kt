package scripts.domain.quest

enum class QuestStatus(val description: String) {
    INACTIVE("비활성화"),
    ACTIVE("활성화"),
    IN_PROGRESS("진행 중"),
    COMPLETED("완료");

    fun isInActive(): Boolean = this == INACTIVE

    fun isActive(): Boolean = this == ACTIVE

    fun isInProgress(): Boolean  = this == IN_PROGRESS
}
