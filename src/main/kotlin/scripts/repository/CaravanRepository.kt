package scripts.repository

import scripts.domain.caravan.Caravan

interface CaravanRepository {
    fun findAll(): List<Caravan>
    fun findPlayerCaravans(): List<Caravan>
    fun addToPlayer(caravan: Caravan)
    fun removeFromPlayer(caravan: Caravan)
    fun update(caravan: Caravan)
    fun addAll(caravans: List<Caravan>)
}
