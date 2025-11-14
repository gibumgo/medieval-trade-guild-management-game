package scripts.domain.common

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import scripts.domain.ErrorMessage

class GoldTest {
    @Test
    @DisplayName("생성 테스트")
    fun createMoneyTest() {
        val money = Gold.of(100)
        assertEquals(100, money.amount)
    }

    @Test
    @DisplayName("음수 금액 입력시 예외 발생")
    fun createMoneyNegativeTest() {
        val exception = assertThrows<IllegalArgumentException>  {
            Gold.of(-1)
        }
        assertEquals(ErrorMessage.MONEY_ERROR, exception.message)
    }
}
