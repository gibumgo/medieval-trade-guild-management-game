package scripts.service

import scripts.domain.caravan.Caravan
import scripts.repository.CaravanRepository


class CaravanService(
    private val caravanRepository: CaravanRepository
) {
    fun availableCaravans(): List<Caravan> =
        caravanRepository.findPlayerCaravans().filter { it.isReady() }

    fun maxAvailableSpeed(): Int =
        availableCaravans().maxOfOrNull { it.speed } ?: DEFAULT_SPEED

    fun updateCaravan(caravan: Caravan) = caravanRepository.update(caravan)

    fun addPlayerCaravan(caravan: Caravan) = caravanRepository.addToPlayer(caravan)

    fun removePlayerCaravan(caravan: Caravan) = caravanRepository.removeFromPlayer(caravan)

    fun selectAndStartTrip(inputIndex: Int): Caravan {
        val caravan = selectAvailableCaravan(inputIndex)
        return startTrip(caravan)
    }

    private fun selectAvailableCaravan(inputIndex: Int): Caravan {
        val caravans = availableCaravans()
        require(inputIndex in 1..caravans.size) { "번호 선택이 범위를 벗어났습니다." }
        return caravans[inputIndex - 1]
    }

    private fun startTrip(caravan: Caravan): Caravan {
        val travelingCaravan = caravan.startTrip()
        updateCaravan(travelingCaravan)
        return travelingCaravan
    }

    companion object {
        private const val DEFAULT_SPEED = 1
    }
}
