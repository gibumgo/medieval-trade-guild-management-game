package scripts.domain.testObject

import scripts.domain.Item.ItemSlots
import scripts.domain.quest.City
import scripts.domain.common.Gold
import scripts.domain.common.ReputationPoint
import scripts.domain.quest.QuestStatus
import scripts.domain.quest.TradeQuest

object TestQuest {
    val quest1 = TradeQuest.of(
        City("북부 도시", 3),
        ItemSlots.of(TestItems.wheat),
        Gold.of(120),
        ReputationPoint.of(2),
        QuestStatus.INACTIVE
    )

    val quest2 = TradeQuest.of(
        City("항구 도시", 5),
        ItemSlots.of(TestItems.wheat),
        Gold.of(70),
        ReputationPoint.of(1),
        QuestStatus.INACTIVE
    )

    val quest3 = TradeQuest.of(
        City("사막 도시", 10),
        ItemSlots.of(listOf(TestItems.wood)),
        Gold.of(10),
        ReputationPoint.of(0),
        QuestStatus.INACTIVE
    )

    val quest4 = TradeQuest.of(
        City("산악 도시", 10),
        ItemSlots.of(listOf(TestItems.iron)),
        Gold.of(150),
        ReputationPoint.of(3),
        QuestStatus.INACTIVE
    )

    val quests = listOf(quest1, quest2, quest3, quest4)
}
