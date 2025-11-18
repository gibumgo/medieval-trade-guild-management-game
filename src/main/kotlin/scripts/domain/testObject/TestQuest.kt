package scripts.domain.testObject

import scripts.domain.common.City
import scripts.domain.common.Gold
import scripts.domain.common.ReputationPoint
import scripts.domain.quest.QuestStatus
import scripts.domain.quest.TradeQuest

object TestQuest {
    val quest1 = TradeQuest(
        city = City("북부 도시", 3),
        requiredItems = TestItems.wheat,
        gold = Gold.of(120),
        reputation = ReputationPoint.of(2),
        status = QuestStatus.INACTIVE
    )

    val quest2 = TradeQuest(
        city = City("항구 도시", 5),
        requiredItems = listOf(TestItems.wood),
        gold = Gold.of(70),
        reputation = ReputationPoint.of(1),
        status = QuestStatus.INACTIVE
    )

    val quest3 = TradeQuest(
        city = City("사막 도시", 10),
        requiredItems = listOf(TestItems.spice),
        gold = Gold.of(200),
        reputation = ReputationPoint.of(5),
        status = QuestStatus.INACTIVE
    )

    val quest4 = TradeQuest(
        city = City("산악 도시", 10),
        requiredItems = listOf(TestItems.iron),
        gold = Gold.of(150),
        reputation = ReputationPoint.of(3),
        status = QuestStatus.INACTIVE
    )

    val quests = listOf(quest1, quest2, quest3, quest4)
}
