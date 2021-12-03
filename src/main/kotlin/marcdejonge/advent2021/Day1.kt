package marcdejonge.advent2021

fun main() = Day1.printSolution()

object Day1 : DaySolver(1) {
    private val numbers = input.map { it.toLong() }.asSequence()

    override fun calcPart1() = numbers.calcIncreasing()

    override fun calcPart2() = numbers.windowed(3).map { it.sum() }.calcIncreasing()

    private fun Sequence<Long>.calcIncreasing() = zipWithNext().count { (x, y) -> x < y }
}
