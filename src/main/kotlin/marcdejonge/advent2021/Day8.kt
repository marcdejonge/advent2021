package marcdejonge.advent2021

import kotlin.streams.asSequence

fun main() = Day8.printSolution()

object Day8 : DaySolver(8) {
    private fun String.parseInputWires() =
        chars().asSequence().map { 1 shl (it - 'a'.code) }.fold(0) { curr, n -> curr or n }

    private val lines = input.map { line ->
        line.split(" | ").map {
            it.split(" ").map { digit -> digit.parseInputWires() }.asSequence()
        }
    }

    private fun Sequence<Int>.findDigit(segments: Int, filter: (Int) -> Boolean = { true }) =
        filter { it.countOneBits() == segments }.single { filter(it) }

    private fun parseWires(digits: Sequence<Int>): Map<Int, Int> {
        val n1 = digits.findDigit(segments = 2)
        val n7 = digits.findDigit(segments = 3)
        val n4 = digits.findDigit(segments = 4)
        val n8 = digits.findDigit(segments = 7)
        val n9 = digits.findDigit(segments = 6) { (it and n4) == n4 }
        val n0 = digits.findDigit(segments = 6) { it != n9 && (it and n1) == n1 }
        val n6 = digits.findDigit(segments = 6) { it != n9 && it != n0 }
        val n3 = digits.findDigit(segments = 5) { (it and n1) == n1 }
        val n5 = digits.findDigit(segments = 5) { it != n3 && (it and n9) == it }
        val n2 = digits.findDigit(segments = 5) { it != n3 && it != n5 }

        return mapOf(n0 to 0, n1 to 1, n2 to 2, n3 to 3, n4 to 4, n5 to 5, n6 to 6, n7 to 7, n8 to 8, n9 to 9)
    }

    private fun Map<Int, Int>.parseDigits(digits: Sequence<Int>) =
        digits.map { this[it]!! }.fold(0) { curr, n -> curr * 10 + n }

    override fun calcPart1() = lines.sumOf { (_, digits) ->
        digits.count { it.countOneBits() in 2..4 || it.countOneBits() == 7 }
    }

    override fun calcPart2() = lines.sumOf { (wires, digits) -> parseWires(wires).parseDigits(digits) }
}
