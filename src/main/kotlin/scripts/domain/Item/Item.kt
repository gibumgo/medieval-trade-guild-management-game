package scripts.domain.Item

data class Item private constructor(val name: String, val weight: Weight) {
    fun weight(quantity: Int): Weight = this.weight.multiply(quantity)

    companion object {
        fun of(name: String, weight: Weight): Item = Item(name, weight)
        fun of(name: String, weight: Int): Item = Item(name, Weight.of(weight))
    }
}
