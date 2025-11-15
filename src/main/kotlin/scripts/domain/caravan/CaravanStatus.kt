package scripts.domain.caravan

enum class CaravanStatus(val description: String) {
    READY("대기중"),
    TRAVELING("이동중"),
    COMPLETED("완료")
}
