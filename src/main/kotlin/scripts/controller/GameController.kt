package scripts.controller

import camp.nextstep.edu.missionutils.Console
import scripts.application.mapper.CaravanMapper
import scripts.application.mapper.InventoryItemMapper
import scripts.application.mapper.PlayerMapper
import scripts.application.mapper.TradeQuestMapper
import scripts.domain.Inventory.Inventory
import scripts.domain.Inventory.InventoryItem
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
import scripts.domain.supply.SupplyBox
import scripts.domain.supply.SupplyBoxType
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
    private val initItem: InventoryItem = InventoryItem.of(Item.of("밀", 1), 10)
    private val initInventory: Inventory = Inventory()


    fun run() {
        gameTime = TurnBasedGameTime()


        initInventory.addAll(listOf(initItem))
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
        val supplyBoxType = supplyService.allSupplyType().map { SupplyBoxMapper.toDTO(it) }
        outputView.printSupplyBoxPurchase(supplyBoxType)


        val wheat = InventoryItem.of(Item.of("밀", 1), 10)
        val availableItems = listOf(wheat)

        if (inputView.isYesInput()) {
            val supplyBox = SupplyBox(SupplyBoxType.BASIC, availableItems)

            val availableItemsDTOList = listOf(InventoryItemMapper.toDTO(wheat))

            outputView.printSupplyBoxResult(availableItemsDTOList)
            val rewardtest = supplyBox.purchaseBy(player)
            player.earnReward(rewardtest)
            outputView.printUpdatedInventory(PlayerMapper.toDTO(player))
        }
    }

    private fun handleQuestAssignment(player: Player) {
        val wheat = InventoryItem.of(Item.of("밀", 1), 10)
        val wood = InventoryItem.of(Item.of("목재", 1), 5)
        val spice = InventoryItem.of(Item.of("향료", 1), 2)
        val iron = InventoryItem.of(Item.of("철", 1), 20)  // 플레이어가 부족해서 수행 불가

        val quest1 = TradeQuest(
            city = City("북부 도시", 3),
            requiredItems = listOf(wheat),
            gold = Gold.of(120),
            reputation = ReputationPoint.of(2),
            status = QuestStatus.INACTIVE
        )

        val quest2 = TradeQuest(
            city = City("항구 도시", 5),
            requiredItems = listOf(wood),
            gold = Gold.of(70),
            reputation = ReputationPoint.of(1),
            status = QuestStatus.INACTIVE
        )

        val quest3 = TradeQuest(
            city = City("사막 도시", 10),
            requiredItems = listOf(spice),
            gold = Gold.of(200),
            reputation = ReputationPoint.of(5),
            status = QuestStatus.INACTIVE
        )

        // 수행 불가 퀘스트 (플레이어가 철 아이템이 없음)
        val quest4 = TradeQuest(
            city = City("산악 도시", 10),
            requiredItems = listOf(iron),
            gold = Gold.of(150),
            reputation = ReputationPoint.of(3),
            status = QuestStatus.INACTIVE
        )

        val quests = listOf(quest1, quest2, quest3, quest4)
        val availableQuests = quests.filter { it.isAvailableFor(player) }
        val questsDTO: List<TradeQuestDTO> = availableQuests
            .map { TradeQuestMapper.toDTO(it) }
        val caravans = player.availableCaravans()

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