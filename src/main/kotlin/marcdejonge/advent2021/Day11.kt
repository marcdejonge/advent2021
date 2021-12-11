package marcdejonge.advent2021

import marcdejonge.advent2021.util.IntMatrix
import marcdejonge.advent2021.util.applyTo
import marcdejonge.advent2021.util.iterate

fun main() = Day11.printSolution()

object Day11 : DaySolver(11) {
    private val startField = IntMatrix(input.map { line -> line.map { it.digitToInt() } })

    private fun step(field: IntMatrix): IntMatrix {
        var nextField = field.map { (_, v) -> v + 1 }
        do { // Iterate to find any flashes
            val changes = nextField.indexed()
                .filter { (_, v) -> v > 9 }
                .flatMap { (ix, _) ->
                    nextField.neighboursDiag(ix).map { (ix, _) -> ix.withValue(1) } + ix.withValue(-100_000)
                }.groupBy { it.index }
                .map { (key, values) -> key.withValue(values.sumOf { it.value } + nextField[key]) }
            nextField = changes.applyTo(nextField)
        } while (changes.isNotEmpty())

        // Now set everyone that has flashed (very low number) to 0
        return nextField.map { (_, v) -> if (v < 0) 0 else v }
    }

    override fun calcPart1() = startField.iterate(::step).drop(1).take(100)
        .sumOf { field -> field.values().count { it == 0 } }

    override fun calcPart2() = startField.iterate(::step)
        .takeWhile { field -> field.values().any { it > 0 } }.count()
}
