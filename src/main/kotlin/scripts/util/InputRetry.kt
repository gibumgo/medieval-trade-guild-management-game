package scripts.utils

object InputRetry {
    tailrec fun <T> read(block: () -> T): T =
        try {
            block()
        } catch (e: IllegalArgumentException) {
            println(e.message)
            read(block)
        }

    tailrec fun <T> retryWithDisplay(display: () -> Unit, block: () -> T?): T? =
        try {
            block()
        } catch (e: IllegalArgumentException) {
            println(e.message)
            display()
            retryWithDisplay(display, block)
        }
}
