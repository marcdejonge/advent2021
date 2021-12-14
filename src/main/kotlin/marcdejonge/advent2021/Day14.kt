package marcdejonge.advent2021

import marcdejonge.advent2021.util.Memoize
import marcdejonge.advent2021.util.combineWith

fun main() = Day14.printSolution()

object Day14 : DaySolver(14) {

    private val template = input.first()
    private val mappingRegex = """([A-Z]{2}) -> ([A-Z])""".toRegex()
    private val mapping = input.drop(2).mapNotNull { mappingRegex.matchEntire(it)?.destructured }
        .associateBy({ it.component1() }, { it.component2().first() })

    private operator fun Map<Char, Long>.plus(other: Map<Char, Long>) = combineWith(other) { a, b -> a + b }

    // This function counts all the characters generated, excluding the ending character (to prevent counting double)
    private val count = Memoize<Pair<String, Int>, Map<Char, Long>> { (pair, stepsLeft), count ->
        if (pair.length == 1) return@Memoize mapOf(pair[0] to 1) // Single end character gets counted separately
        if (stepsLeft == 0) mapOf(pair[0] to 1) // No more steps down, so only count the starting character
        else mapping[pair]!!.let { insertedChar -> // Then recursively map to the next 2 pairs and sum the results
            count("${pair[0]}$insertedChar" to (stepsLeft - 1)) + count("$insertedChar${pair[1]}" to (stepsLeft - 1))
        }
    }

    private fun String.countMap(steps: Int) = windowed(2, partialWindows = true).asSequence()
        .map { count(it to steps) }.fold(mapOf<Char, Long>()) { acc, next -> acc + next }

    private val Iterable<Long>.maxDiff get() = maxOf { it } - minOf { it }

    override fun calcPart1() = template.countMap(10).values.maxDiff
    override fun calcPart2() = template.countMap(40).values.maxDiff
}
