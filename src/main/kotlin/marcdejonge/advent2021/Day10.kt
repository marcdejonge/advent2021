package marcdejonge.advent2021

import marcdejonge.advent2021.util.Stack
import marcdejonge.advent2021.util.middle
import marcdejonge.advent2021.util.sumOfNotNull

fun main() = Day10.printSolution()

object Day10 : DaySolver(10) {
    private val validPairs = arrayOf("[]", "()", "<>", "{}")
    infix fun Char.pairsWith(other: Char) = "$this$other" in validPairs

    data class MatchingPairs(val stack: Stack<Char> = Stack.empty(), val invalidChars: Stack<Char> = Stack.empty()) {
        fun addStart(startChar: Char) = copy(stack = stack.add(startChar))
        fun addEnd(endChar: Char) = MatchingPairs(
            stack.pop(),
            if (stack.peek() pairsWith endChar) invalidChars else invalidChars.add(endChar)
        )
    }

    private fun String.matchingPairs() = fold(MatchingPairs()) { current, char ->
        when (char) {
            '[', '(', '<', '{' -> current.addStart(char)
            ']', ')', '>', '}' -> current.addEnd(char)
            else -> current
        }
    }

    private val parsedLines by lazy { input.map { it.matchingPairs() }.asSequence() }

    private val part1Score = mapOf(')' to 3L, ']' to 57L, '}' to 1197L, '>' to 25137L)
    override fun calcPart1() = parsedLines.sumOf { it.invalidChars.sumOfNotNull(part1Score::get) }

    private val part2Score = mapOf('(' to 1L, '[' to 2L, '{' to 3L, '<' to 4L)
    override fun calcPart2() = parsedLines.filter { it.invalidChars.depth == 0 }
        .map { it.stack.mapNotNull(part2Score::get).toList().foldRight(0L) { next, acc -> next + acc * 5 } }.middle()
}
