package scripts.service

import scripts.domain.common.Gold
import scripts.domain.player.Player
import scripts.domain.time.TurnBasedGameTime

class DailyRoutineService {
    private val gameTime = TurnBasedGameTime()
    private val BASE_CARAVAN_SALARY_PER_DAY = 50


    fun today(): Int {
        return gameTime.currentDay()
    }

    fun progressDay() {
        gameTime.advance()
    }

    fun calculateDailyCost(player: Player): Int {
        val inventoryCost = player.calculateCost()
        val caravanCost = player.caravansLength() * BASE_CARAVAN_SALARY_PER_DAY

        val dailyCost = inventoryCost + caravanCost
        player.pay(Gold.of(dailyCost))
        return dailyCost
    }
}
