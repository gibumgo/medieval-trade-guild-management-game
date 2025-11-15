package scripts.view

import camp.nextstep.edu.missionutils.Console


class InputView {
    fun isYesInput(): Boolean {
        return Console.readLine().trim().uppercase() == "Y"
    }

    fun inputSelectNumber () : Int{
        return Console.readLine().trim().toInt()
    }


}
