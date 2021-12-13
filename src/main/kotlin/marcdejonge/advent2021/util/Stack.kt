package marcdejonge.advent2021.util

interface Stack<T> : Sequence<T> {
    val depth: Int
    fun add(item: T): Stack<T>
    fun pop(): Stack<T>
    fun peek(): T

    override fun iterator() = object : Iterator<T> {
        private var stack: Stack<T> = this@Stack

        override fun hasNext() = stack.depth > 0

        override fun next(): T = stack.peek().also { this.stack = stack.pop() }
    }

    companion object {
        @Suppress("UNCHECKED_CAST") // This just works because the Empty object doesn't store anything
        fun <T> empty(): Stack<T> = Empty as Stack<T>

        operator fun <T> invoke(single: T): Stack<T> = NonEmpty(single, empty())
    }

    private object Empty : Stack<Any> {
        override val depth get() = 0
        override fun add(item: Any) = NonEmpty(item, this)
        override fun pop() = throw NoSuchElementException("Empty stack")
        override fun peek() = throw NoSuchElementException("Empty stack")
        override fun toString() = "[]"
    }

    private class NonEmpty<T>(
        private val item: T,
        private val rest: Stack<T>,
    ) : Stack<T> {
        override val depth = rest.depth + 1
        override fun add(item: T) = NonEmpty(item, this)
        override fun pop() = rest
        override fun peek() = item
        override fun toString(): String = joinToString(",", "[", "]")
    }
}
