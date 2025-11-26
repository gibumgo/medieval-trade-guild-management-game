package scripts.domain.common

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GameDayTest {
    @Test
    @DisplayName("날짜가 0이면 정상 생성된다")
    fun `날짜가 0이면 정상 생성된다`() {
        val gameDay = GameDay.from(0)
        assertThat(gameDay.day).isEqualTo(0)
    }

    @Test
    @DisplayName("날짜가 음수이면 생성에 실패한다")
    fun `날짜가 음수이면 생성에 실패한다`() {
        val exception = assertThrows<IllegalArgumentException> {
            GameDay.from(-1)
        }
        assertEquals("날짜는 음수가 될 수 없습니다.", exception.message)
    }

    @Test
    @DisplayName("날짜가 Int 최대값이면 정상 생성된다")
    fun `날짜가 Int 최대값이면 정상 생성된다`() {
        val gameDay = GameDay.from(Int.MAX_VALUE)
        assertThat(gameDay.day).isEqualTo(Int.MAX_VALUE)
    }

    @Test
    @DisplayName("nextDay를 호출하면 다음 날로 진행된다")
    fun `nextDay를 호출하면 다음 날로 진행된다`() {
        val today = GameDay.from(10)
        val tomorrow = today.nextDay()
        assertThat(tomorrow.day).isEqualTo(11)
    }

    @Test
    @DisplayName("다른 GameDay를 빼면 지난 일수를 반환한다")
    fun `다른 GameDay를 빼면 지난 일수를 반환한다`() {
        val day10 = GameDay.from(10)
        val day3 = GameDay.from(3)
        assertThat(day10 - day3).isEqualTo(7)
    }

    @Test
    @DisplayName("정수 일수를 빼면 그만큼 이전 날짜가 된다")
    fun `정수 일수를 빼면 그만큼 이전 날짜가 된다`() {
        val day100 = GameDay.from(100)
        val result = day100 - 37
        assertThat(result.day).isEqualTo(63)
    }

    @Test
    @DisplayName("같은 날짜면 isSameDay는 true를 반환한다")
    fun `같은 날짜면 isSameDay는 true를 반환한다`() {
        val day1 = GameDay.from(42)
        val day2 = GameDay.from(42)
        assertThat(day1.isSameDay(day2)).isTrue
        assertThat(day1).isEqualTo(day2)
    }

    @Test
    @DisplayName("이전 날짜면 isBefore는 true를 반환한다")
    fun `이전 날짜면 isBefore는 true를 반환한다`() {
        val earlier = GameDay.from(10)
        val later = GameDay.from(20)
        assertThat(earlier.isBefore(later)).isTrue
        assertThat(later.isBefore(earlier)).isFalse
    }

    @Test
    @DisplayName("이후 날짜면 isAfter는 true를 반환한다")
    fun `이후 날짜면 isAfter는 true를 반환한다`() {
        val earlier = GameDay.from(10)
        val later = GameDay.from(20)
        assertThat(later.isAfter(earlier)).isTrue
        assertThat(earlier.isAfter(later)).isFalse
    }

    @Test
    @DisplayName("start로 만든 시작일과 from(0)으로 만든 값은 같다")
    fun `start로 만든 시작일과 from(0)으로 만든 값은 같다`() {
        assertThat(GameDay.start()).isEqualTo(GameDay.from(0))
    }
}
