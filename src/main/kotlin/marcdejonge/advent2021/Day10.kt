package marcdejonge.advent2021

import marcdejonge.advent2021.util.middle
import marcdejonge.advent2021.util.sumOfNotNull

fun main() = Day10.printSolution()

object Day10 : DaySolver(10) {
    private val validStrings = listOf("[]", "()", "<>", "{}")
    private val startChars = validStrings.map { it[0] }.toCharArray()
    private val endChars = validStrings.map { it[1] }.toCharArray()

    data class MatchingPairs(
        val stack: MutableList<Char> = ArrayList(),
        val invalidChars: MutableList<Char> = ArrayList()
    ) {
        private fun pushToStack(char: Char) = stack.add(char)

        private fun handleClosingChar(char: Char) {
            if ("${stack.removeLast()}$char" !in validStrings) invalidChars.add(char)
        }

        fun parseString(line: String) = line.forEach { char ->
            when (char) {
                in startChars -> pushToStack(char)
                in endChars -> handleClosingChar(char)
            }
        }
    }

    private val parseLines by lazy { input.map { MatchingPairs().apply { parseString(it) } }.asSequence() }

    private val part1Score = mapOf(')' to 3L, ']' to 57L, '}' to 1197L, '>' to 25137L)
    override fun calcPart1() = parseLines.sumOf { it.invalidChars.sumOfNotNull(part1Score::get) }

    private val part2Score = mapOf('(' to 1L, '[' to 2L, '{' to 3L, '<' to 4L)
    override fun calcPart2() = parseLines.filter { it.invalidChars.isEmpty() }
        .map { it.stack.mapNotNull(part2Score::get).foldRight(0L) { next, acc -> next + acc * 5 } }.middle()
}
