package scripts.domain.quest

enum class QuestStatus(val description: String) {
    INACTIVE("비활성화"),
    ACTIVE("활성화"),
    ACCEPTED("수락됨"),
    IN_PROGRESS("진행 중"),
    COMPLETED("완료"),
}
