package scripts

import scripts.controller.GameController
import scripts.service.PlayerStatusService
import scripts.service.SupplyService
import scripts.view.InputView
import scripts.view.OutputView

fun main() {

    val gameController = GameController(
        inputView = InputView(),
        outputView = OutputView(),
        playerStatusService = PlayerStatusService(),
        supplyService = SupplyService()
    )

    gameController.run()
}
