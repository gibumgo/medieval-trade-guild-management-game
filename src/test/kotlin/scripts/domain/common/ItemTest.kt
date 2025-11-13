package scripts.domain.common

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ItemTest {
    @Test
    @DisplayName("생성 테스트")
    fun creatTest() {
        val item = Item.of("밀", 1)
        assertEquals(item, Item.of("밀", 1))
    }
}
