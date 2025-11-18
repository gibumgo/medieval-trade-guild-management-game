package scripts.domain.Item

import scripts.domain.common.Weight

data class Item private constructor(val name: String, val weight: Weight) {

    companion object {
        fun of(name: String, weight: Weight): Item = Item(name, weight)
        fun of(name: String, weight: Int): Item = Item(name, Weight.of(weight))
    }
}
