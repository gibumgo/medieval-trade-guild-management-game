package scripts.domain.reward

import scripts.domain.testObject.TestItems

class BasicRewardGenerator : RewardGenerator {
    override fun generate(): Rewards {
        return Rewards.of(
            items = TestItems.wheat
        )
    }
}
