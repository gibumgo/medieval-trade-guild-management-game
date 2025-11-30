package scripts.controller

import camp.nextstep.edu.missionutils.Console
import scripts.domain.caravan.Caravan
import scripts.domain.player.Player
import scripts.domain.quest.Quest
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

        try {
            gameLoop(player)
        } catch (e: IllegalArgumentException) {
            handleGameException(e)
        } finally {
            println("game over")
            Console.close()
        }
    }

    private fun gameLoop(player: Player) {
        while (true) {
            dailyRoutine(player)
            if (playerStatusService.isBankrupt()) {
                println("골드가 바닥났습니다. 게임 종료!")
                break
            }
            if (!inputView.waitForEnterOnly()) break
        }
    }

    private fun handleGameException(e: IllegalArgumentException) {
        println("[게임 종료] ${e.message}")
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
        val questDTOs = QuestMapper.toDTOs(availableQuests, maxSpeed)
        outputView.printAvailableQuests(questDTOs)
        outputView.printQuestSelectionGuide()
        val selectedQuest = InputRetry.retryWithDisplay(
            display = { outputView.printQuestSelectionGuide() }
        ) { handleQuestsInput() } ?: return

        val caravans = caravanService.availableCaravans()
        outputView.printAvailableCaravans(CaravanMapper.toDTOs(caravans))
        outputView.printCaravanSelectionGuide()
        val caravan = InputRetry.retryWithDisplay(
            display = { outputView.printCaravanSelectionGuide() }
        ) { handleCaravanInput() } ?: return

        val assignedQuest = questService.assignQuest(player, selectedQuest, caravan)
        outputView.printAssignedQuest(
            QuestDeliveryMapper.toDTO(assignedQuest)
        )
    }

    private fun handleCaravanInput(): Caravan {
        val input = inputView.inputSelectNumber()
        return caravanService.selectAndStartTrip(input)
    }

    private fun handleQuestsInput(): Quest? {
        val input = inputView.inputSelectNumber()
        if (input == 0) {
            outputView.printNotSelectedQuests()
            return null
        }
        return questService.selectedQuest(input)
    }

    private fun progressAssignedQuest(player: Player) {
        val inProgressQuests = questService.updateAllDeliveries()
        outputView.printAssignedQuestProgress(QuestDeliveryMapper.toDTOs(inProgressQuests))

        val completedQuests = questService.collectCompletedQuests(player)
        outputView.printCompleteQuests(QuestDeliveryMapper.toDTOs(completedQuests))

        completedQuests.forEach { completedQuest ->
            caravanService.returnCaravan(completedQuest.returnCaravan())
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