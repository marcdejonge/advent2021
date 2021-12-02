package marcdejonge.advent2021

import marcdejonge.advent2021.Day2.Command.*

fun main() = Day2().printSolution()

class Day2 : DaySolver(2) {
    enum class Command {
        FORWARD,
        UP,
        DOWN,
    }

    private val regex = """(\w+) (\d+)""".toRegex()

    private val commands = input.parse {
        mapNotNull {
            regex.find(it)?.destructured?.let { (command, size) ->
                valueOf(command.uppercase()) to size.toLong()
            }
        }
    }

    data class Direction(
        val horizontal: Long = 0,
        val depth: Long = 0,
        val aim: Long = 0,
    ) {
        val result get() = horizontal * depth
    }

    override fun calcPart1() =
        commands.fold(Direction()) { (horizontal, depth), (command, size) ->
            when (command) {
                FORWARD -> Direction((horizontal + size), depth)
                DOWN -> Direction(horizontal, (depth + size))
                UP -> Direction(horizontal, (depth - size))
            }
        }.result

    override fun calcPart2() =
        commands.fold(Direction()) { (horizontal, depth, aim), (command, size) ->
            when (command) {
                FORWARD -> Direction((horizontal + size), depth + (aim * size), aim)
                DOWN -> Direction(horizontal, depth, (aim + size))
                UP -> Direction(horizontal, depth, (aim - size))
            }
        }.result
}
