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

    companion object {
        private const val DEFAULT_SPEED = 1
    }
}
