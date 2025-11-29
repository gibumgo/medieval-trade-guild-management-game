package scripts.view

import scripts.dto.QuestDeliveryDTO
import scripts.dto.CaravanDTO
import scripts.dto.ItemSlotDTO
import scripts.dto.PlayerDTO
import scripts.dto.SupplyBoxDTO
import scripts.dto.QuestDTO
import scripts.util.GameText

class OutputView {
    fun printCurrentDay(today: Int) =
        TextBuilder()
            .title("DAY $today : ${GameText.DAY_REPORT_TITLE}")
            .print()

    fun printPlayerStatus(playerDTO: PlayerDTO, CaravanCount: Int) =
        TextBuilder()
            .line("${GameText.LABEL_GOLD} ${playerDTO.gold} G")
            .line("${GameText.LABEL_REPUTATION} ${playerDTO.reputation}")
            .line("${GameText.LABEL_CAPACITY} ${playerDTO.capacityUsed} / ${playerDTO.capacityMax}")
            .line("${GameText.LABEL_CARAVAN_COUNT} $CaravanCount 대")
            .line(GameText.LINE_SEPARATOR)
            .print()

    fun printInventory(inventoryDTO: List<ItemSlotDTO>) {
        val items = mutableListOf<String>()

        if (inventoryDTO.isEmpty()) {
            items.add(GameText.CURRENT_INVENTORY_EMPTY)
        }
        inventoryDTO.forEach { items.add("- ${it.name} : ${it.quantity}") }

        TextBuilder()
            .line(GameText.CURRENT_INVENTORY)
            .lines(items)
            .line(GameText.LINE_SEPARATOR)
            .print()
    }

    fun printSupplyBoxPrompt() =
        TextBuilder()
            .npc(GameText.GUILD_MASTER_NAME, GameText.GUILD_MASTER_GREETING)
            .print()

    fun printSupplyBoxOptions(supplyBoxDTOs: List<SupplyBoxDTO>) =
        TextBuilder()
            .line(GameText.SUPPLY_BOX_OPTIONS)
            .lines(supplyBoxDTOs.mapIndexed { index, box ->
                "  (${index + 1}) ${box.displayName.padEnd(10)} | ${
                    box.price.toString().padStart(5)
                } G | 필요 명성: ${box.minReputation}"
            })
            .print()

    fun printSupplyBoxInputGuide() = print(GameText.SELECT_PROMPT)

    fun printSupplyBoxResult(items: List<ItemSlotDTO>) =
        TextBuilder()
            .line(GameText.SUPPLY_OPEN_SUCCESS)
            .line(
                GameText.SUPPLY_ITEMS_RECEIVED.replace(
                    "{items}",
                    items.joinToString { "${it.name} ${it.quantity}개" })
            )
            .print()

    fun printUpdatedInventory(status: PlayerDTO) =
        TextBuilder()
            .title(GameText.INVENTORY_UPDATE)
            .lines(status.inventory.map { "- ${it.name}: ${it.quantity}" })
            .line("- 골드 : ${status.gold} G")
            .print()

    fun printNotSelectedSupplyBox() = println(GameText.SUPPLY_BOX_REJECTED)

    fun printAvailableQuests(quests: List<QuestDTO>) {
        if (quests.isEmpty()) {
            println(GameText.QUEST_NO_SELECTION)
            return
        }
        TextBuilder()
            .title(GameText.QUEST_REPORT_TITLE)
            .lines(quests.mapIndexed { i, q ->
                "[${i + 1}] ${q.city} : ${q.requiredItems.joinToString { "${it.name} ${it.quantity}개" }} 납품\n" +
                        "   보상: ${q.rewardGold} G / 명성 +${q.rewardReputation}\n" +
                        "   ${"최소 소요 기간: ${q.durationDays}일"}\n" +
                        GameText.LINE_SEPARATOR
            })
            .print()
    }

    fun printQuestSelectionGuide() = print(GameText.SELECT_PROMPT)

    fun printNotSelectedQuests() = println(GameText.QUEST_REJECTED)

    fun printAvailableCaravans(caravans: List<CaravanDTO>) =
        TextBuilder()
            .line(GameText.SELECT_CARAVAN_PROMPT)
            .lines(caravans.mapIndexed { i, c -> "[${i + 1}] ${c.name}" })
            .print()

    fun printCaravanSelectionGuide() = print(GameText.SELECT_PROMPT)

    fun printAssignedQuest(assignedQuest: QuestDeliveryDTO) =
        TextBuilder()
            .line("${assignedQuest.caravan.name} 가(이) ${assignedQuest.quest.city} 로 출정합니다.")
            .line(GameText.ASSIGNED_QUEST_DEPART)
            .line(GameText.LINE_SEPARATOR)
            .print()

    fun printAssignedQuestProgress(assignedQuests: List<QuestDeliveryDTO>) {
        if (assignedQuests.isEmpty()) {
            println(GameText.NO_ONGOING_QUESTS)
            return
        }
        TextBuilder()
            .line(GameText.QUEST_DISPATCHED)
            .lines(assignedQuests.map { assignedQuest ->
                "[${assignedQuest.quest.status}] ${assignedQuest.caravan.name} - ${assignedQuest.quest.city} " +
                        "(남은 일수: ${assignedQuest.progressDay} / ${assignedQuest.totalDays}일)\n" +
                        "보상: ${assignedQuest.quest.rewardGold} G, 명성 +${assignedQuest.quest.rewardReputation}"
            })
            .line(GameText.LINE_SEPARATOR)
            .print()
    }

    fun printCompleteQuests(completeQuests: List<QuestDeliveryDTO>) {
        if (completeQuests.isEmpty()) return
        TextBuilder()
            .line(GameText.QUEST_COMPLETE)
            .lines(completeQuests.map { quest ->
                "${quest.caravan.name}가 ${quest.quest.city}에서 귀환했습니다.\n" +
                        " + ${quest.quest.rewardGold} G 획득\n" +
                        " + 명성 +${quest.quest.rewardReputation} 증가"
            })
            .print()
    }

    fun printDaySummary(day: Int, cost: Int, currentGold: Int, specialEvent: String? = null) =
        TextBuilder()
            .title(GameText.DAY_END_TITLE.replace("{day}", day.toString()))
            .line(GameText.DAY_END_COST.replace("{cost}", cost.toString()))
            .line(GameText.DAY_END_CURRENT_GOLD.replace("{gold}", currentGold.toString()))
            .line(specialEvent ?: GameText.DAY_END_SPECIAL_EVENT_NONE)
            .line(GameText.LINE_SEPARATOR)
            .line(GameText.DAY_END_NEXT)
            .print()
}
