package marcdejonge.advent2021

import marcdejonge.advent2021.util.Index2D
import marcdejonge.advent2021.util.IntMatrix
import marcdejonge.advent2021.util.Memoize

fun main() = Day9.printSolution()

object Day9 : DaySolver(9) {
    private val heights = IntMatrix(input.map { it.toCharArray().map { c -> c.digitToInt() } })

    override fun calcPart1() = heights.indexed()
        .filter { (index, value) -> heights.directNeighbours(index).all { it.value > value } }
        .sumOf { (_, value) -> 1 + value }

    private val findLowestPoint = Memoize<Index2D, Index2D> { index, self ->
        heights.directNeighbours(index).minByOrNull { (_, nValue) -> nValue }?.let {
            if (it.value > heights[index]) index // Low point found
            else self(it.index)
        } ?: index
    }

    private fun <T> Sequence<T>.countSame() = groupingBy { it }.fold(0) { count, _ -> count + 1 }
    private fun Iterable<Int>.product() = fold(1) { curr, next -> curr * next }

    override fun calcPart2() = heights.indexed().filter { it.value < 9 }.map { findLowestPoint(it.index) }
        .countSame().values // Count how many of each index is found and just use the counts
        .sortedDescending().take(3).product() // Take the product of the top 3 counts
}
