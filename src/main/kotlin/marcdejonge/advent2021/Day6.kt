package marcdejonge.advent2021

import marcdejonge.advent2021.util.Memoize

fun main() = Day6.printSolution()

object Day6 : DaySolver(6) {
    private val fishes = input.first().split(",").map { it.toInt() }

    private val countFishesAfter = Memoize<Int, Long> { generation, self ->
        1 + ((generation - 9) downTo 0 step 7).sumOf { self(it) }
    }

    override fun calcPart1() = fishes.sumOf { countFishesAfter(80 + 8 - it) }
    override fun calcPart2() = fishes.sumOf { countFishesAfter(256 + 8 - it) }
}
