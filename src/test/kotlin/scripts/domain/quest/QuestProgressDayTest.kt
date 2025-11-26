package scripts.domain.quest

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class QuestProgressDayTest {
    @Test
    fun `start로 생성하면 currentDay는 0부터 시작한다`() {
        val progress = QuestProgressDay.start(5)

        assertEquals(0, progress.currentDay.day)
        assertEquals(5, progress.totalTravelDays.day)
        assertFalse(progress.isCompleted())
    }

    @Test
    fun `nextDay를 호출하면 하루씩 진행된다`() {
        var progress = QuestProgressDay.start( 3)

        progress = progress.nextDay()
        assertEquals(1, progress.currentDay.day)
    }

    @Test
    @DisplayName("시작일이 총 여행 날짜보다 크면 생성에 실패한다")
    fun `잘못된 생성 시 예외 발생`() {
        val exception = assertThrows<IllegalArgumentException> {
            val questDay = QuestProgressDay.start(1)
            questDay.nextDay()
            questDay.nextDay()
        }
        assertEquals("진행 날짜는 총 여행일 보다 미만 이여야 합니다.", exception.message)
    }
}
