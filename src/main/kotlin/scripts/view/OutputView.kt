package scripts.view

import scripts.dto.AssignedQuestDTO
import scripts.dto.CaravanDTO
import scripts.dto.ItemSlotDTO
import scripts.dto.PlayerDTO
import scripts.dto.SupplyBoxDTO
import scripts.dto.TradeQuestDTO

class OutputView {
    fun printCurrentDay(today: Int) {
        println("[Day ${today}]")
    }

    fun printPlayerStatus(playerDTO: PlayerDTO) {
        println("[상단 현황]")
        println("골드(G): ${playerDTO.gold}")
        println("명성: ${playerDTO.reputation}")
        println("창고: ${playerDTO.capacityUsed} / ${playerDTO.capacityMax}")
        println("보유 행상대: ${playerDTO.caravans.size}대")
    }

    fun printInventory(inventoryDTO: List<ItemSlotDTO>) {
        println("현재 보유 재고:")
        printItems(inventoryDTO)
    }

    fun printSupplyBoxPurchase(supplyBoxDTOs: List<SupplyBoxDTO>) {
        println("길드 관리관이 찾아왔습니다.")
        println("“오늘도 행운을 빕니다. 무작위 보급 상자를 하나 구입하시겠습니까?”")
        println("선택 가능한 보급 상자:")
        supplyBoxDTOs.forEachIndexed { index, type ->
            println("[${index + 1}] ${type.displayName} - 비용 : ${type.price}G, 필요 명성 : ${type.minReputation}")
            println("-------------------------------------")
        }
        print("(번호 선택, 0 = 모두 거절) > ")
    }

    fun printSupplyBoxResult(items: List<ItemSlotDTO>) {
        println("보급 상자를 열었습니다!")
        println("획득: ${printDetailedItems(items)}")
        println()
    }

    fun printUpdatedInventory(status: PlayerDTO) {
        println("업데이트된 재고:")
        printItems(status.inventory)
        println("- 골드(G): ${status.gold}")
    }

    fun printNotSelectedSupplyBox() {
        println("보급 상자 구매를 거절합니다.")
    }

    fun printAvailableQuests(quests: List<TradeQuestDTO>) {
        if (quests.isEmpty()) {
            println("가능한 거래 목록이 없습니다.")
            return
        }
        println("오늘의 거래 의뢰서가 도착했습니다.")
        println("주문 의뢰 - 가능한 거래 목록")
        quests.forEachIndexed { selectIndex, quest ->
            printQuestStatusDetail(quest, selectIndex)
        }
        printQuestSelection()
    }

    private fun printQuestStatusDetail(quest: TradeQuestDTO, selectIndex: Int) {
        val items = printDetailedItems(quest.requiredItems)

        println("[${selectIndex + 1}] ${quest.city} : $items 납품")
        println("   보상: ${quest.rewardGold}골드 / 명성 +${quest.rewardReputation}")
        println("   최소 소요 기간: ${quest.durationDays}일")
        println("-------------------------------------")
    }

    private fun printQuestSelection() {
        println("수락할 주문 번호를 선택하세요 (0 = 모두 거절):")
        print(" > ")
    }

    fun printNotSelectedQuests() {
        println("퀘스트 수락을 거절합니다.")
    }

    fun printAvailableCaravans(caravans: List<CaravanDTO>) {
        println()
        println("배정할 행상대를 선택하세요")
        caravans.forEachIndexed { index, caravan ->
            println("[${index + 1}] ${caravan.name}")
        }
        print("> ")
    }

    fun printAssignedQuest(assignedQuest: AssignedQuestDTO) {
        println()
        println(" ${assignedQuest.caravan.name} 가(이) ${assignedQuest.quest.city} 로 출정합니다.")
        println()
        println("출발 중...")
        println()
        println("\n-----------------")
    }

    fun printAssignedQuestProgress(assignedQuests: List<AssignedQuestDTO>) {
        if (assignedQuests.isEmpty()) {
            println("현재 진행 중인 퀘스트가 없습니다.")
            return
        }
        println("파견된 상단의 현황")
        assignedQuests.forEach { assignedQuest ->
            printQuestStatusDetail(assignedQuest)
        }
        println("=======================")
    }


    fun printCompleteQuests(completeQuests: List<AssignedQuestDTO>) {
        if (completeQuests.isEmpty()) return
        println("[퀘스트 완료]")
        completeQuests.forEach { quest ->
            println("${quest.caravan.name}가 ${quest.quest.city}에서 귀환했습니다.")
            println("보상: +${quest.quest.rewardGold}골드, +${quest.quest.rewardReputation}명성")
            println()
        }
    }

    private fun printQuestStatusDetail(assignedQuest: AssignedQuestDTO) {
        println(
            "[${assignedQuest.caravan.status}]"
                    + "${assignedQuest.caravan.name} - ${assignedQuest.quest.city}"
                    + "(남은 일수: ${assignedQuest.progressDay} / ${assignedQuest.totalDays}일)"
        )
        println(
            "보상: ${assignedQuest.quest.rewardGold}G,"
                    + "+${assignedQuest.quest.rewardReputation} 명성"
        )
        println()
    }


    fun printDaySummary(
        day: Int,
        cost: Int,
        currentGold: Int,
        specialEvent: String? = null
    ) {
        println("\n-----------------")
        println("=== Day $day ===")
        println("창고 유지비 및 급료 지출: -$cost 골드")
        println("현재 골드 : $currentGold 골드")
        if (!specialEvent.isNullOrBlank()) {
            println(specialEvent)
        } else {
            println("특별한 사건은 없었습니다.")
        }
        println("\n하루를 종료합니다.")
        print("계속하려면 Enter 키를 누르세요 (0 = 종료)")
    }

    private fun printItems(items: List<ItemSlotDTO>) {
        when {
            items.isEmpty() -> println("없음")
            else -> items.forEach { println("- ${it.name}: ${it.quantity}") }
        }
    }

    private fun printDetailedItems(items: List<ItemSlotDTO>): String {
        return items.joinToString(separator = ", ") { "${it.name} ${it.quantity}개" }
    }
}
