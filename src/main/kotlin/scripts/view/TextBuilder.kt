package scripts.view

class TextBuilder {
    private val lines = mutableListOf<String>()

    fun title(text: String): TextBuilder {
        lines.add("==================================================")
        lines.add("  $text")
        lines.add("==================================================")
        return this
    }

    fun line(text: String): TextBuilder {
        lines.add(text)
        return this
    }

    fun lines(texts: List<String>): TextBuilder {
        lines.addAll(texts)
        return this
    }

    fun npc(name: String, text: String): TextBuilder {
        lines.add("[$name] \"$text\"")
        return this
    }

    fun warning(text: String): TextBuilder {
        lines.add("[경고] $text")
        return this
    }

    fun blank(): TextBuilder {
        lines.add("")
        return this
    }

    fun print() {
        lines.forEach { println(it) }
        lines.clear()
    }
}
