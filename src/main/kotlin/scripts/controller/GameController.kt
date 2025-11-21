package scripts.controller

import camp.nextstep.edu.missionutils.Console
import scripts.domain.player.Player
import scripts.dto.*
import scripts.mapper.*
import scripts.service.*
import scripts.view.InputView
import scripts.view.OutputView

class GameController(
    private val inputView: InputView,
    private val outputView: OutputView,
    private val playerStatusService: PlayerStatusService,
    private val supplyService: SupplyService,
    private val questService: QuestService,
    private val dailyRoutineService: DailyRoutineService,
) {
    fun run() {
        val player = playerStatusService.player()

        while (true) {
            dailyRoutine(player)
            if (!inputView.waitForEnterOnly()) break
        }

        println("game over")
        Console.close()
    }

    private fun dailyRoutine(player: Player) {
        outputView.printCurrentDay(dailyRoutineService.today())

        val playerDTO = PlayerMapper.toDTO(player)
        outputView.printPlayerStatus(playerDTO)
        outputView.printInventory(playerDTO.inventory)

        handleSupplyBoxPurchase()
        handleQuestAssignment(player)
        progressAssignedQuest(player)
        endDay(player)
    }

    private fun handleSupplyBoxPurchase() {
        val supplyBoxTypes = supplyService.allSupplyType()
        outputView.printSupplyBoxPurchase(SupplyBoxMapper.toDTOs(supplyBoxTypes))
        val selectedNumber = inputView.inputSelectNumber()
        if (selectedNumber == 0) {
            outputView.printNotSelectedSupplyBox()
            return
        }
        val supplyBox = supplyService.openSupplyBox(selectedNumber)
        val reward = playerStatusService.receiveSupplyBox(supplyBox)
        outputView.printSupplyBoxResult(ItemSlotMapper.toDTO(reward))
        outputView.printUpdatedInventory(PlayerMapper.toDTO(playerStatusService.player()))
    }

    private fun handleQuestAssignment(player: Player) {
        questService.fillerActive(playerStatusService.currentInventory())
        val availableQuests = questService.availableQuest()
        if (availableQuests.isEmpty()) {
            outputView.printAvailableQuests(emptyList())
            return
        }

        val questsDTO: List<TradeQuestDTO> = TradeQuestMapper.toDTOs(availableQuests, player)
        outputView.printAvailableQuests(questsDTO)

        val selectedNumber = inputView.inputSelectNumber()
        if (selectedNumber == 0) {
            outputView.printNotSelectedQuests()
            return
        }

        val selectedQuest = questService.selectedQuest(selectedNumber)

        val caravans = playerStatusService.availableCaravans()
        outputView.printAvailableCaravans(CaravanMapper.toDTOs(caravans))
        val selectCaravan = inputView.inputSelectNumber()
        val caravan = caravans[selectCaravan - 1]

        val assignedQuest = selectedQuest.assignTo(player, caravan)
        outputView.printAssignedQuest(
            AssignedQuestMapper.toDTO(assignedQuest)
        )
        player.addActiveQuests(assignedQuest)
    }

    private fun progressAssignedQuest(player: Player) {
        questService.processQuestProgress(player)
        outputView.printAssignedQuestProgress(
            AssignedQuestMapper.toDTOs(playerStatusService.activeQuests())
        )
        outputView.printCompleteQuests(
            AssignedQuestMapper.toDTOs(playerStatusService.completeQuests())
        )

        player.removeCompletedQuests()
        player.progressDay()
    }

    private fun endDay(player: Player) {
        outputView.printDaySummary(
            dailyRoutineService.today(),
            dailyRoutineService.calculateDailyCost(player),
            player.currentGold(),
            null
        )
        dailyRoutineService.progressDay()
    }
}