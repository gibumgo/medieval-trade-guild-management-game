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

}
