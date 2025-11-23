package scripts.domain.reward

fun interface RewardGenerator {
    fun generate(): Rewards
}
