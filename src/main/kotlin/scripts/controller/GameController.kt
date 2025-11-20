package scripts.controller

import camp.nextstep.edu.missionutils.Console
import scripts.domain.common.Gold
import scripts.domain.player.Player
import scripts.domain.time.GameTime
import scripts.domain.time.TurnBasedGameTime
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
) {
    private lateinit var gameTime: GameTime
    private val WAREHOUSE_COST_PER_DAY = 50
    private val TOTAL_CARAVAN_SALARY_PER_DAY = 100

    fun run() {
        gameTime = TurnBasedGameTime()
        val player = playerStatusService.player()

        while (true) {
            dailyRoutine(player)
            if (!inputView.waitForEnterOnly()) break
        }

        println("game over")
        Console.close()
    }

    private fun dailyRoutine(player: Player) {
        outputView.printCurrentDay(gameTime)

        val playerDTO = PlayerMapper.toDTO(player)
        outputView.printPlayerStatus(playerDTO)
        outputView.printInventory(playerDTO.inventory)

        handleSupplyBoxPurchase()
        handleQuestAssignment(player)
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
        val assignedQuestDTO = AssignedQuestMapper.toDTO(assignedQuest)
        outputView.printAssignedQuest(assignedQuestDTO)
        player.addActiveQuests(assignedQuest)

        outputView.printAssignedQuestProgress(
            AssignedQuestMapper.toDTOs(playerStatusService.activeQuests())
        )

        questService.processQuestProgress(player)

        //완료된 퀘스트 출력문 추가
    }

    private fun endDay(player: Player) {
        val dailyCost = WAREHOUSE_COST_PER_DAY + TOTAL_CARAVAN_SALARY_PER_DAY
        player.pay(Gold.of(dailyCost))

        outputView.printDaySummary(
            gameTime.currentDay(),
            dailyCost,
            player.currentGold(),
            null
        )
        gameTime.advance()
    }
}