package marcdejonge.advent2021.util

import kotlin.math.absoluteValue

data class LineSegment(val start: IntVector2, val end: IntVector2) {
    private val direction = end - start

    val isStraight get() = (direction.x == 0) or (direction.y == 0)
    val isDiagonal get() = direction.x.absoluteValue == direction.y.absoluteValue

    private inline val Int.step get() = if (this == 0) 0 else this / this.absoluteValue

    val indices: Sequence<IntVector2>
        get() {
            assert(isStraight || isDiagonal) { "withIndices only works with straight or diagonal lines" }
            val stepVector = direction.x.step v direction.y.step
            return generateSequence(start) { it + stepVector }.takeWhile { it != end } + sequenceOf(end)
        }

    override fun toString() = "$start-$end"
}
