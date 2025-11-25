package scripts.domain.quest

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CityTest {
    @Test
    fun calculateTravelTime() {
        val city = City("테스트 도시", 6)

        assertEquals(3, city.calculateTravelTime(2))
        assertEquals(2, city.calculateTravelTime(4))
        assertEquals(1, city.calculateTravelTime(6))
        assertEquals(1, city.calculateTravelTime(100))
    }
}
