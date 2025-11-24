package scripts.domain.reward

import scripts.domain.Item.Item
import scripts.domain.Item.ItemSlot


class BasicRewardGenerator : RewardGenerator {
    override fun generate(): Rewards {
        return Rewards.of(
            items = listOf(
                ItemSlot.of(Item.of("밀", 1), 10),
                ItemSlot.of(Item.of("목재", 1), 5)
            )
        )
    }
}
