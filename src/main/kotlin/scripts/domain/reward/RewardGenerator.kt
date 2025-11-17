package scripts.domain.reward

fun interface RewardGenerator {
    fun generate(): Reward
}
