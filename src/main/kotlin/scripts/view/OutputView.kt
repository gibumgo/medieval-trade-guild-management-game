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

    fun printSupplyBoxPurchase() {
        println("길드 관리관이 찾아왔습니다.")
        println("“오늘도 행운을 빕니다. 무작위 보급 상자를 하나 구입하시겠습니까?”")
        print("구입하시겠습니까? (Y/N) > ")
    }

    fun printSupplyBoxResult(items: List<InventoryItemDTO>) {
        println("보급 상자를 열었습니다!")
        println("획득:")
        printDetailedItems(items)
        println()
    }

    fun printUpdatedInventory(status: PlayerDTO) {
        println("업데이트된 재고:")
        printItems(status.inventory)
        println("- 골드(G): ${status.gold}")
    }

    fun printAvailableQuests(quests: List<TradeQuestDTO>) {
        println("오늘의 거래 의뢰서가 도착했습니다.")
        println("주문 의뢰 - 가능한 거래 목록")

        quests.forEachIndexed { selectIndex, quest ->
            val items = printDetailedItems(quest.requiredItems)

            println("[${selectIndex + 1}] ${quest.city} : $items 납품")
            println("   보상: ${quest.rewardGold}골드 / 명성 +${quest.rewardReputation}")
            println("   소요 기간: ${quest.durationDays}일")
            println("-------------------------------------")
        }
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
        return items.joinToString(separator = ", ") { "${it.name} ${it.quantity}개" }
    }
}
