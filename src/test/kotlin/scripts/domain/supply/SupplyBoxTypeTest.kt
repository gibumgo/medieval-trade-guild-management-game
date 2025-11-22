package scripts.domain.supply

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SupplyBoxTypeTest {
    @Test
    @DisplayName("from 메서드로 선택 번호 변환")
    fun testFromSelectNumber() {
        assertEquals(SupplyBoxType.BASIC, SupplyBoxType.from(1))
        assertEquals(SupplyBoxType.ADVANCED, SupplyBoxType.from(2))
        assertEquals(SupplyBoxType.ROYAL, SupplyBoxType.from(3))
        assertEquals(SupplyBoxType.LEGENDARY, SupplyBoxType.from(4))
    }

    @Test
    @DisplayName("유효하지 않은 번호 선택 시 예외 발생")
    fun testInvalidSelectionNumber() {
        assertThrows<IllegalArgumentException> { SupplyBoxType.from(0) }
        assertThrows<IllegalArgumentException> { SupplyBoxType.from(5) }
        assertThrows<IllegalArgumentException> { SupplyBoxType.from(-1) }
    }
}
