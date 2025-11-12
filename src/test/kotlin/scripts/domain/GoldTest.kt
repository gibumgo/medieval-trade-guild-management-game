package scripts.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GoldTest {
    @Test
    @DisplayName("생성 테스트")
    fun createMoneyTest() {
        val money = Gold.of(100)
        assertEquals(100, money.amount)
    }

    @Test
    @DisplayName("0 이하 금액 입력시 예외 발생")
    fun invalidTest() {
        val exception = assertThrows<IllegalArgumentException>  {
            Gold.of(0)
        }
        assertEquals(ErrorMessage.MONEY_ERROR, exception.message)
    }

    @Test
    @DisplayName("음수 금액 입력시 예외 발생")
    fun createMoneyNegativeTest() {
        val exception = assertThrows<IllegalArgumentException>  {
            Gold.of(-50)
        }
        assertEquals(ErrorMessage.MONEY_ERROR, exception.message)
    }
}
