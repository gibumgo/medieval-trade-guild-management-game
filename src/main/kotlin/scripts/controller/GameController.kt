package scripts.controller

import camp.nextstep.edu.missionutils.Console
import scripts.application.mapper.CaravanMapper
import scripts.application.mapper.ItemSlotMapper
import scripts.application.mapper.PlayerMapper
import scripts.application.mapper.TradeQuestMapper
import scripts.domain.common.Gold
import scripts.mapper.AssignedQuestMapper
import scripts.mapper.SupplyBoxMapper
import scripts.domain.player.Player
import scripts.domain.quest.AssignedQuest
import scripts.domain.testObject.TestQuest
import scripts.domain.time.GameTime
import scripts.domain.time.TurnBasedGameTime
import scripts.dto.*
import scripts.service.PlayerStatusService
import scripts.service.SupplyService
import scripts.view.InputView
import scripts.view.OutputView

class GameController(
    private val inputView: InputView,
    private val outputView: OutputView,
    private val playerStatusService: PlayerStatusService,
    private val supplyService: SupplyService,
) {
    private lateinit var gameTime: GameTime
    private val assignedQuests: MutableList<AssignedQuest> = mutableListOf()
    private val WAREHOUSE_COST_PER_DAY = 50
    private val TOTAL_CARAVAN_SALARY_PER_DAY = 100

    fun run() {
        gameTime = TurnBasedGameTime()


        while (true) {
            dailyRoutine()
            if (!inputView.waitForEnterOnly()) break
        }

        println("game over")
        Console.close()
    }

    private fun dailyRoutine() {
        outputView.printCurrentDay(gameTime)

        val playerDTO = PlayerMapper.toDTO(playerStatusService.status())
        outputView.printPlayerStatus(playerDTO)
        outputView.printInventory(playerDTO.inventory)

        handleSupplyBoxPurchase()
        handleQuestAssignment(playerStatusService.status())
    }

    private fun handleSupplyBoxPurchase() {
        val supplyBoxTypes = supplyService.allSupplyType()
        outputView.printSupplyBoxPurchase(SupplyBoxMapper.toDTOs(supplyBoxTypes))
        val supplyBox = supplyService.openSupplyBox(inputView.inputSelectNumber())
        val reward = playerStatusService.receiveSupplyBox(supplyBox)
        outputView.printSupplyBoxResult(ItemSlotMapper.toDTO(reward))
        outputView.printUpdatedInventory(PlayerMapper.toDTO(playerStatusService.status()))
    }

    private fun handleQuestAssignment(player: Player) {

        val availableQuests = TestQuest.quests.filter { it.isAvailableFor(player) }
        val questsDTO: List<TradeQuestDTO> = TradeQuestMapper.toDTOs(availableQuests)
        outputView.printAvailableQuests(questsDTO)
        outputView.printQuestSelection()
        val selectNumber = inputView.inputSelectNumber()
        val selectedQuest = availableQuests[selectNumber - 1]

        val caravans = playerStatusService.availableCaravans()
        outputView.printAvailableCaravans(CaravanMapper.toDTOs(caravans))
        val selectCaravan = inputView.inputSelectNumber()
        val caravan = caravans[selectCaravan - 1]

        val assignedQuest = selectedQuest.assignTo(player, caravan)
        outputView.printAssignedQuestProgress(AssignedQuestMapper.toDTO(assignedQuest))

        assignedQuests.add(assignedQuest)
        assignedQuests.forEach { assignedQuest -> assignedQuest.progressOneDay() }

        val completedQuests = assignedQuests.filter { it.isCompleted() }

        completedQuests.forEach { aq ->
            aq.completed().applyTo(player)
            aq.resetToReady()
        }

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