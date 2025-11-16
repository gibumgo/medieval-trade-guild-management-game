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
        while (true) {
            val input = Console.readLine().trim()
            if (input.isEmpty()) {
                return true
            }
            if (input.toInt() == 0) {
                println("프로그램을 종료합니다.")
            }
            println("엔터만 눌러주세요. (0 = 종료)")
        }
    }
}