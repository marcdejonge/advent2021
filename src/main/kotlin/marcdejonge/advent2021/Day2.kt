package marcdejonge.advent2021

import marcdejonge.advent2021.Day2.Command.*

fun main() = Day2.printSolution()

object Day2 : DaySolver(2) {
    enum class Command {
        FORWARD,
        UP,
        DOWN,
    }

    private val regex = """(\w+) (\d+)""".toRegex()

    private val commands = input.mapNotNull {
        regex.find(it)?.destructured?.let { (command, size) ->
            valueOf(command.uppercase()) to size.toLong()
        }
    }

    data class Position(
        val horizontal: Long = 0,
        val depth: Long = 0,
        val aim: Long = 0,
    ) {
        val result get() = horizontal * depth
    }

    override fun calcPart1() =
        commands.fold(Position()) { current, (command, size) ->
            when (command) {
                FORWARD -> current.copy(horizontal = current.horizontal + size)
                DOWN -> current.copy(depth = current.depth + size)
                UP -> current.copy(depth = current.depth - size)
            }
        }.result

    override fun calcPart2() =
        commands.fold(Position()) { current, (command, size) ->
            when (command) {
                FORWARD -> current.copy(
                    horizontal = current.horizontal + size,
                    depth = current.depth + (current.aim * size)
                )
                DOWN -> current.copy(aim = current.aim + size)
                UP -> current.copy(aim = current.aim - size)
            }
        }.result
}
