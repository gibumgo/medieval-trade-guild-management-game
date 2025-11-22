package scripts.view

import camp.nextstep.edu.missionutils.Console


class InputView {
    fun isYesInput(): Boolean {
        return Console.readLine().trim().equals("Y", ignoreCase = true)
    }

    fun inputSelectNumber(): Int {
        return Console.readLine().trim().toInt()
    }

    fun waitForEnterOnly(): Boolean {
        val input = Console.readLine().trim()
        return input != "0"
    }
}