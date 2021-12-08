package marcdejonge.advent2021

import kotlin.streams.asSequence

fun main() = Day8.printSolution()

object Day8 : DaySolver(8) {
    private fun String.parseInputWires() =
        chars().asSequence().map { 1 shl (it - 'a'.code) }.fold(0) { curr, n -> curr or n }

    private val lines = input.map { line ->
        line.split(" | ").map {
            it.split(" ").map { digit -> digit.parseInputWires() }
        }
    }

    private fun parseWires(digits: List<Int>): Map<Int, Int> {
        val n1 = digits.single { it.countOneBits() == 2 }
        val n7 = digits.single { it.countOneBits() == 3 }
        val n4 = digits.single { it.countOneBits() == 4 }
        val n8 = digits.single { it.countOneBits() == 7 }

        val n3 = digits.single { it.countOneBits() == 5 && (it and n1) == n1 }
        val leftTopBar = n4 xor (n3 and n4)
        val n5 = digits.single { it.countOneBits() == 5 && (it and leftTopBar) != 0 }
        val rightLowBar = n5 and n1
        val n2 = digits.single { it.countOneBits() == 5 && (it and rightLowBar) == 0 }
        val rightTopBar = n1 xor rightLowBar
        val n6 = digits.single { it.countOneBits() == 6 && (it and rightTopBar) == 0 }
        val leftLowBar = n6 xor n5
        val n9 = digits.single { it.countOneBits() == 6 && (it and leftLowBar) == 0 }
        val n0 = digits.single { it != n6 && it != n9 && it.countOneBits() == 6 }

        return mapOf(n0 to 0, n1 to 1, n2 to 2, n3 to 3, n4 to 4, n5 to 5, n6 to 6, n7 to 7, n8 to 8, n9 to 9)
    }

    private fun Map<Int, Int>.parseDigits(digits: List<Int>) =
        digits.asSequence().map { this[it]!! }.fold(0) { curr, n -> curr * 10 + n }

    override fun calcPart1() = lines.sumOf { (_, digits) ->
        digits.count { it.countOneBits() in 2..4 || it.countOneBits() == 7 }
    }

    override fun calcPart2() = lines.sumOf { (wires, digits) -> parseWires(wires).parseDigits(digits) }
}
