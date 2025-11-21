package scripts.domain.caravan

enum class CaravanStatus(val displayName: String) {
    READY("대기중"),
    TRAVELING("이동중"),
    COMPLETED("완료")
}
