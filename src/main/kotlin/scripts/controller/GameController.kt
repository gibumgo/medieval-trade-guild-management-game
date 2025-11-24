package scripts.controller

import camp.nextstep.edu.missionutils.Console
import scripts.domain.caravan.Caravan
import scripts.domain.player.Player
import scripts.domain.quest.TradeQuest
import scripts.domain.reward.Rewards
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
    private val caravanService: CaravanService
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
        outputView.printPlayerStatus(playerDTO, caravanService.availableCaravans().size)
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
        ) { handleSupplyBoxInput() } ?: return

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

        val maxSpeed = caravanService.maxAvailableSpeed()
        val questDTOs = TradeQuestMapper.toDTOs(availableQuests, maxSpeed)
        outputView.printAvailableQuests(questDTOs)

        val selectedQuest = InputRetry.retryWithDisplay(
            display = { outputView.printQuestSelectionGuide() }
        ) { handleQuestsInput() } ?: return

        val caravans = caravanService.availableCaravans()
        outputView.printAvailableCaravans(CaravanMapper.toDTOs(caravans))

        val caravan = InputRetry.retryWithDisplay(
            display = { outputView.printCaravanSelectionGuide() }
        ) { handleCaravanInput() }?: return

        val assignedQuest = questService.assignQuest(player, selectedQuest, caravan)
        outputView.printAssignedQuest(
            AssignedQuestMapper.toDTO(assignedQuest)
        )
    }

    private fun handleCaravanInput(): Caravan {
        val input = inputView.inputSelectNumber()
        return caravanService.selectAndStartTrip(input)
    }

    private fun handleQuestsInput(): TradeQuest? {
        val input = inputView.inputSelectNumber()
        if (input == 0) {
            outputView.printNotSelectedQuests()
            return null
        }
        return questService.selectedQuest(input)
    }

    private fun progressAssignedQuest(player: Player) {
        val inProgressQuests = questService.getInProgressQuests()
        outputView.printAssignedQuestProgress(AssignedQuestMapper.toDTOs(inProgressQuests))

        val completedQuests = questService.collectCompletedQuests(player)
        outputView.printCompleteQuests(AssignedQuestMapper.toDTOs(completedQuests))

        completedQuests.forEach { completedQuest ->
            val returnedCaravan = completedQuest.caravanLeader()
            caravanService.returnCaravan(returnedCaravan)
        }
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