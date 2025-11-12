package scripts.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CapacityTest {
    @Test
    @DisplayName("정상 생성 테스트")
    fun createCapacityTest() {
        val capacity = Capacity.of(10, 50)
        assertEquals(50, capacity.max)
        assertEquals(10, capacity.current)
    }

    @Test
    @DisplayName("현재 용량이 최대 용량보다 크면 예외 발생")
    fun invalidCapacityExceedsMaxTest() {
        assertThrows<IllegalArgumentException> {
            Capacity.of(60, 50)
        }
    }

    @Test
    @DisplayName("현재 용량이 음수면 예외 발생")
    fun invalidCapacityNegativeValueTest() {
        assertThrows<IllegalArgumentException> {
            Capacity.of(-1, 50)
        }
    }
}
