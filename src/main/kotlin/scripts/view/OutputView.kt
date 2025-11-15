package scripts.view

import scripts.domain.time.GameTime
import scripts.dto.InventoryItemDTO
import scripts.dto.PlayerDTO
import scripts.dto.TradeQuestDTO

class OutputView {
    fun printCurrentDay(gameTime: GameTime) {
        println("[Day ${gameTime.currentDay()}]")
        println("")
    }

    fun printPlayerStatus(playerDTO: PlayerDTO) {
        println("[상단 현황]")
        println("골드(G): ${playerDTO.gold}")
        println("명성: ${playerDTO.reputation}")
        println("창고: ${playerDTO.capacityUsed} / ${playerDTO.capacityMax}")
        println("보유 행상대: ${playerDTO.caravans.size}대")
    }

}
