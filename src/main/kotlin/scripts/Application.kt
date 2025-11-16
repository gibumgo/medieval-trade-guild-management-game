package scripts

import scripts.controller.GameController
import scripts.view.InputView
import scripts.view.OutputView

fun main() {
    val InputView: InputView = InputView()
    val OutputView: OutputView = OutputView()
    val gameController = GameController(InputView, OutputView)

    gameController.run()
}
