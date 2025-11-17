package scripts.domain.reward

import scripts.domain.testObject.TestItems

class BasicRewardGenerator: RewardGenerator {
    override fun generate(): Reward {
        return Reward.ofItems(TestItems.wheat)
    }
}
