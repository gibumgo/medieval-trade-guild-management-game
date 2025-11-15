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

    fun printInventory(inventory: List<InventoryItemDTO>) {
        println("현재 보유 재고:")
        printItems(inventory)
    }


    fun printQuestSelection(quests: List<TradeQuestDTO>) {
        println("수락할 주문 번호를 선택하세요 (0 = 모두 거절):")
        print(" > ")
    }

    private fun printItems(items: List<InventoryItemDTO>) {
        when {
            items.isEmpty() -> println("없음")
            else -> items.forEach { println("- ${it.name}: ${it.quantity}") }
        }
    }

    private fun printDetailedItems(items: List<InventoryItemDTO>): String {
        return items.joinToString(separator = ", ") { "${it.name} ${it.quantity}" }
    }
}
