package scripts.domain.common

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

    @Test
    @DisplayName("plus 연산 테스트")
    fun plusTest() {
        val w1 = Weight.of(5)
        val w2 = Weight.of(3)
        val result = w1.plus(w2)
        assertEquals(8, result.weight)
    }

    @Test
    @DisplayName("minus 연산 테스트")
    fun minusTest() {
        val w1 = Weight.of(10)
        val w2 = Weight.of(4)
        val result = w1.minus(w2)
        assertEquals(6, result.weight)
    }

    @Test
    @DisplayName("minus 연산 후 0이 되는 경우")
    fun minusToZeroTest() {
        val w1 = Weight.of(5)
        val w2 = Weight.of(5)
        val result = w1.minus(w2)
        assertTrue(result.isZero())
    }
}
