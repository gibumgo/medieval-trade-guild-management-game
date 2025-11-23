package scripts.controller

import camp.nextstep.edu.missionutils.Console
import scripts.domain.player.Player
import scripts.domain.reward.Rewards
import scripts.dto.*
import scripts.mapper.*
import scripts.service.*
import scripts.utils.InputRetry
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
        val supplyBoxDTOs = SupplyBoxMapper.toDTOs(supplyBoxTypes)

        outputView.printSupplyBoxPrompt()
        outputView.printSupplyBoxOptions(supplyBoxDTOs)
        outputView.printSupplyBoxInputGuide()
        val rewards = InputRetry.retryWithDisplay(
            display = { outputView.printSupplyBoxInputGuide() }
        ) {
            handleSupplyBoxInput()
        } ?: return

        outputView.printSupplyBoxResult(ItemSlotMapper.toDTO(rewards))
        outputView.printUpdatedInventory(PlayerMapper.toDTO(playerStatusService.player()))
    }

    private fun handleSupplyBoxInput(): Rewards? {
        val inputNumber = inputView.inputSelectNumber()

        if (inputNumber == 0) {
            outputView.printNotSelectedSupplyBox()
            return null
        }
        val supplyBox = supplyService.openSupplyBox(inputNumber)
        return playerStatusService.receiveSupplyBox(supplyBox)
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

        val assignedQuest = questService.assignQuest(player, selectedQuest, caravan)
        outputView.printAssignedQuest(
            AssignedQuestMapper.toDTO(assignedQuest)
        )
    }

    private fun progressAssignedQuest(player: Player) {
        outputView.printAssignedQuestProgress(
            AssignedQuestMapper.toDTOs(questService.getInProgressQuests())
        )
        outputView.printCompleteQuests(
            AssignedQuestMapper.toDTOs(questService.collectCompletedQuests(player))
        )
        questService.rollDayForQuests()
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