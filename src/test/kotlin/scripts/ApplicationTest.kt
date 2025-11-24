package scripts

import camp.nextstep.edu.missionutils.test.NsTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ApplicationTest : NsTest() {
    @Test
    @DisplayName("2일 진행 시 출력 확인")
    fun dayTwoProgressTest() {
        val input = listOf(
            "1", "1", "1", "",
            "1", "1", "1", "0"
        )

        run(*input.toTypedArray())

        val output = output()
        assert(output.contains("DAY 1 : 상단 보고"))
        assert(output.contains("DAY 2 : 상단 보고"))
        assert(output.contains("로반의 행상대 가(이) 북부 도시 로 출정합니다."))
        assert(output.contains("+ 120 G 획득"))
        assert(output.contains("game over"))
    }

    override fun runMain() {
        main()
    }
}
