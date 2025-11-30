package scripts

import scripts.controller.GameController
import scripts.domain.time.TurnBasedGameTime
import scripts.repository.CaravanRepositoryImpl
import scripts.repository.QuestDeliveryRepositoryImpl
import scripts.repository.QuestRepositoryImpl
import scripts.service.CaravanService
import scripts.service.DailyRoutineService
import scripts.service.PlayerStatusService
import scripts.service.QuestService
import scripts.service.SupplyService
import scripts.view.InputView
import scripts.view.OutputView

fun main() {
    val caravanRepo = CaravanRepositoryImpl()
    val questRepo = QuestRepositoryImpl()
    val deliveryRepo = QuestDeliveryRepositoryImpl()
    val time = TurnBasedGameTime()

    val gameController = GameController(
        inputView = InputView(),
        outputView = OutputView(),
        playerStatusService = PlayerStatusService(),
        supplyService = SupplyService(),
        questService = QuestService(questRepo, deliveryRepo),
        dailyRoutineService = DailyRoutineService(time, caravanRepo),
        caravanService = CaravanService(caravanRepo),
    )

    gameController.run()
}
