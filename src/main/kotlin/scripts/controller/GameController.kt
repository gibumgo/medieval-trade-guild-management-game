package scripts.controller

import camp.nextstep.edu.missionutils.Console
import scripts.application.mapper.CaravanMapper
import scripts.application.mapper.ItemSlotMapper
import scripts.application.mapper.PlayerMapper
import scripts.application.mapper.TradeQuestMapper
import scripts.domain.common.ItemSlot
import scripts.domain.common.City
import scripts.domain.common.Gold
import scripts.domain.common.Item
import scripts.domain.common.ReputationPoint
import scripts.mapper.AssignedQuestMapper
import scripts.mapper.SupplyBoxMapper
import scripts.domain.player.Player
import scripts.domain.quest.AssignedQuest
import scripts.domain.quest.QuestStatus
import scripts.domain.quest.TradeQuest
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

        val player = playerStatusService.status()
        val playerDTO = PlayerMapper.toDTO(player)

        outputView.printPlayerStatus(playerDTO)
        outputView.printInventory(playerDTO.inventory)

        handleSupplyBoxPurchase(player)
        handleQuestAssignment(player)
    }

    private fun handleSupplyBoxPurchase(player: Player) {
        val supplyBoxTypes = supplyService.allSupplyType()
        outputView.printSupplyBoxPurchase(SupplyBoxMapper.toDTOs(supplyBoxTypes))

        val reward = supplyService.openSupplyBox(inputView.inputSelectNumber(), player)
        outputView.printSupplyBoxResult(ItemSlotMapper.toDTO(reward))
        player.earnReward(reward)
        outputView.printUpdatedInventory(PlayerMapper.toDTO(player))
    }

    private fun handleQuestAssignment(player: Player) {

        val availableQuests = quests.filter { it.isAvailableFor(player) }
        val questsDTO: List<TradeQuestDTO> = availableQuests
            .map { TradeQuestMapper.toDTO(it) }
        val caravans = player.availableCaravans()
        TestItems()
        outputView.printAvailableQuests(questsDTO)
        outputView.printQuestSelection()
        val selectNumber = inputView.inputSelectNumber()
        val selectedQuest = availableQuests[selectNumber - 1]
        val caravansDTO = caravans.map { CaravanMapper.toDTO(it) }
        outputView.printAvailableCaravans(caravansDTO)
        val selectCaravan = inputView.inputSelectNumber()
        val caravan = caravans[selectCaravan - 1]

        val assignedQuestTest = selectedQuest.assignTo(player, caravan)
        outputView.printAssignedQuestProgress(AssignedQuestMapper.toDTO(assignedQuestTest))

        assignedQuests.add(assignedQuestTest)
        // 퀘스트 진행 및 완료 처리
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