package marcdejonge.advent2021

import marcdejonge.advent2021.util.IntMatrix
import marcdejonge.advent2021.util.applyTo
import marcdejonge.advent2021.util.findFirstUnchanged
import marcdejonge.advent2021.util.iterate

fun main() = Day11.printSolution()

object Day11 : DaySolver(11) {
    val startField = IntMatrix(input.map { line -> line.map { it.digitToInt() } })

    private fun flashOctopuses(field: IntMatrix) = field.indexed().filter { (_, value) -> value > 9 }
        .flatMap { (ix, _) -> field.allNeighbours(ix).map { (nIx, _) -> nIx.withValue(1) } + ix.withValue(-1000) }
        .groupBy { it.index } // Group all the changed by the index
        .map { (key, values) -> key.withValue(values.sumOf { it.value } + field[key]) }
        .applyTo(field)

    private fun step(field: IntMatrix) =
        field.map { (_, v) -> v + 1 } // First add 1 to every value
            .iterate(::flashOctopuses) // Then keep flashing all the octopuses
            .findFirstUnchanged() // And find the first time nothing changes
            .map { (_, v) -> if (v < 0) 0 else v } // Then make sure all low values are set to zero

    override fun calcPart1() = startField.iterate(::step).drop(1).take(100)
        .sumOf { field -> field.values().count { it == 0 } }

    override fun calcPart2() = startField.iterate(::step)
        .takeWhile { field -> field.values().any { it > 0 } }.count()
}
