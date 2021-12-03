package marcdejonge.advent2021

fun main() = Day3.printSolution()

object Day3 : DaySolver(3) {
    private val numbers = input.map { it.toInt(2) }
    private val bitLength = input.first().length

    private val allBits = generateSequence(1 shl (bitLength - 1)) { it shr 1 }.takeWhile { it > 0 }
    private fun List<Int>.hasMajority(bit: Int) = count { it and bit != 0 } * 2 >= size
    private fun Sequence<Int>.accumulate() = fold(0) { acc, x -> acc or x }

    override fun calcPart1(): Int {
        val gamma = allBits.filter { numbers.hasMajority(it) }.accumulate()
        val epsilon = allBits.filter { !numbers.hasMajority(it) }.accumulate()
        return gamma * epsilon
    }

    private fun generateFilteredLists(filter: List<Int>.(Int) -> Boolean) =
        allBits.runningFold(numbers) { numbers, bit ->
            if (filter(numbers, bit)) numbers.filter { it and bit != 0 }
            else numbers.filter { it and bit == 0 }
        }

    private fun Sequence<List<Int>>.findFirstSingleNumber() = first { it.size == 1 }.first()

    override fun calcPart2(): Int {
        val oxygen = generateFilteredLists { bit -> hasMajority(bit) }.findFirstSingleNumber()
        val co2 = generateFilteredLists { bit -> !hasMajority(bit) }.findFirstSingleNumber()
        return oxygen * co2
    }
}
