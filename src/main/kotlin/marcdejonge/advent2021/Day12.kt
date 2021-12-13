package marcdejonge.advent2021

import marcdejonge.advent2021.util.Stack
import marcdejonge.advent2021.util.contains
import marcdejonge.advent2021.util.toMapGroup

fun main() = Day12.printSolution()

object Day12 : DaySolver(12) {
    private val lineRegex = """(\w+)-(\w+)""".toRegex()
    private val smallCave = """[a-z]+""".toRegex()
    private val connections = input.mapNotNull { lineRegex.matchEntire(it)?.destructured?.let { (a, b) -> a to b } }
    private val nextSteps = (connections + connections.map { (a, b) -> b to a }).toMapGroup() // Paths work both ways

    data class State(val cave: String, val canPostDuplicate: Boolean)

    private fun expand(past: Stack<State>): Sequence<Stack<State>> = past.peek().let { (lastCave, canPostDuplicate) ->
        if (lastCave == "end") sequenceOf(past)
        else nextSteps[lastCave]?.asSequence()?.mapNotNull { nextCave ->
            val isDuplicate = smallCave.matches(nextCave) && past.contains { it.cave == nextCave }
            if ((!canPostDuplicate || nextCave == "start") && isDuplicate) null
            else past.add(State(nextCave, canPostDuplicate && !isDuplicate))
        }?.flatMap { expand(it) } ?: emptySequence()
    }

    override fun calcPart1() = expand(Stack(State("start", false))).count()
    override fun calcPart2() = expand(Stack(State("start", true))).count()
}
