package marcdejonge.advent2021

import kotlin.math.absoluteValue

fun main() = Day7.printSolution()

object Day7 : DaySolver(7) {
    private val crabPositions = input.first().split(",").map { it.toInt() }

    private val Int.triangle get() = this * (this + 1) / 2

    override fun calcPart1() = (0..1000).minOf { pos ->
        crabPositions.sumOf { (it - pos).absoluteValue }
    }

    override fun calcPart2() = (0..1000).minOf { pos ->
        crabPositions.sumOf { (it - pos).absoluteValue.triangle }
    }
}
