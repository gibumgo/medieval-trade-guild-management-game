package scripts.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ReputationPointTest {
    @Test
    @DisplayName("생성 테스트")
    fun createpointTest() {
        val point = ReputationPoint.of(100)
        assertEquals(100, point.point)
    }

    @Test
    @DisplayName("0 이하 금액 입력시 예외 발생")
    fun invalidTest() {
        val exception = assertThrows<IllegalArgumentException> {
            ReputationPoint.of(0)
        }
        assertEquals(ErrorMessage.REPUTATION_POINT_ERROR, exception.message)
    }

    @Test
    @DisplayName("음수 금액 입력시 예외 발생")
    fun createpointNegativeTest() {
        val exception = assertThrows<IllegalArgumentException>  {
            ReputationPoint.of(-50)
        }
        assertEquals(ErrorMessage.REPUTATION_POINT_ERROR, exception.message)
    }
}