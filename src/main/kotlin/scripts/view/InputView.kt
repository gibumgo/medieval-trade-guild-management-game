package scripts.view

import camp.nextstep.edu.missionutils.Console


class InputView {
    fun isYesInput(): Boolean {
        return Console.readLine().trim().equals("Y", ignoreCase = true)
    }

    fun inputSelectNumber(): Int = parseInt(Console.readLine())

    private fun parseInt(input: String): Int {
        return input.trim().toIntOrNull() ?: throw NumberFormatException("숫자를 입력해주세요.")
    }

    fun waitForEnterOnly(): Boolean {
        val input = Console.readLine().trim()
        return input != "0"
    }
}