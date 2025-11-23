package scripts.repository

import kotlinx.serialization.json.Json
import scripts.domain.caravan.Caravan
import scripts.dto.CaravanDTO
import scripts.mapper.CaravanMapper
import java.io.File

class CaravanRepositoryImpl : CaravanRepository {
    private val allCaravans: MutableList<Caravan> by lazy {
        val caravans: List<CaravanDTO> = Json.decodeFromString(
            File(
                "src/main/resources/gameData/caravans.json"
            ).readText()
        )
        caravans.map { CaravanMapper.fromDTO(it) }.toMutableList()
    }

    private val playerCaravans: MutableList<Caravan> by lazy {
        val defaultLeaders = listOf("로반", "리아")
        allCaravans.filter { it.leader in defaultLeaders }.toMutableList()
    }

    override fun findAll(): List<Caravan> = allCaravans.toList()

    override fun findPlayerCaravans(): List<Caravan> = playerCaravans.toList()

    override fun addToPlayer(caravan: Caravan) {
        if (playerCaravans.none { it.leader == caravan.leader }) {
            playerCaravans.add(caravan)
        }
    }

    override fun removeFromPlayer(caravan: Caravan) {
        playerCaravans.removeIf { it.leader == caravan.leader }
    }

    override fun update(caravan: Caravan) {
        val indexPlayer = playerCaravans.indexOfFirst { it.leader == caravan.leader }
        if (indexPlayer >= 0) playerCaravans[indexPlayer] = caravan
    }

    override fun addAll(caravans: List<Caravan>) {
        allCaravans.addAll(caravans)
    }
}
