package marcdejonge.advent2021

import marcdejonge.advent2021.util.IntVector2
import marcdejonge.advent2021.util.v

fun main() = Day13.printSolution()

object Day13 : DaySolver(13) {
    private val pointRegex = """(\d+),(\d+)""".toRegex()
    private val points = input.asSequence().takeWhile { it != "" }.mapNotNull {
        pointRegex.matchEntire(it)?.destructured?.let { (x, y) -> (x.toInt() v y.toInt()) }
    }.toSet()

    enum class Direction { X, Y }

    private val instructionRegex = """fold along ([xy])=(\d+)""".toRegex()
    private val instructions = input.subList(input.indexOf("") + 1, input.size).mapNotNull {
        instructionRegex.matchEntire(it)?.destructured?.let { (direction, place) ->
            Direction.valueOf(direction.uppercase()) to place.toInt()
        }
    }

    private fun Set<IntVector2>.fold(instruction: Pair<Direction, Int>) = instruction.let { (dir, amount) ->
        mapTo(HashSet()) { p ->
            when (dir) {
                Direction.X -> if (p.x < amount) p else (amount * 2 - p.x) v p.y
                Direction.Y -> if (p.y < amount) p else p.x v (amount * 2 - p.y)
            }
        }
    }

    override fun calcPart1() = points.fold(instructions.first()).size

    override fun calcPart2() = instructions.fold(points) { current, instr -> current.fold(instr) }.niceString()

    private fun Set<IntVector2>.niceString() = (0..maxOf { it.y }).joinToString("\n", "\n") { y ->
        (0..maxOf { it.x }).joinToString("") { x -> if (contains(x v y)) "██" else "  " }
    }
}
