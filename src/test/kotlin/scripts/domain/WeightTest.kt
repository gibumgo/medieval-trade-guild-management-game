package scripts.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class WeightTest {

    @Test
    @DisplayName("정상적인 Weight 생성")
    fun createWeightTest() {
        val weight = Weight.of(10)
        assertEquals(10, weight.weight)
    }

    @Test
    @DisplayName("0 이상의 Weight 생성 가능")
    fun zeroWeightTest() {
        val weight = Weight.of(0)
        assertTrue(weight.isZero())
    }

    @Test
    @DisplayName("음수 Weight 생성 시 예외 발생")
    fun negativeWeightTest() {
        assertThrows<IllegalArgumentException> {
            Weight.of(-1)
        }
    }

}
