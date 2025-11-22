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
        assert(output.contains("[Day 1]"))
        assert(output.contains("[Day 2]"))
        assert(output.contains("로반의 행상대가 북부 도시에서 귀환했습니다."))
        assert(output.contains("보상: +120골드"))
        assert(output.contains("game over"))
    }

    override fun runMain() {
        main()
    }
}
