package marcdejonge.advent2021

import kotlin.math.absoluteValue

fun main() = Day7.printSolution()

object Day7 : DaySolver(7) {
    private val crabPositions = input.first().split(",").map { it.toInt() }.toIntArray()
    private val possiblePositions = crabPositions.minOf { it }..crabPositions.maxOf { it }

    private val Int.triangle get() = this * (this + 1) / 2

    override fun calcPart1() = possiblePositions.minOf { position ->
        crabPositions.sumOf { (it - position).absoluteValue }
    }

    override fun calcPart2() = possiblePositions.minOf { position ->
        crabPositions.sumOf { (it - position).absoluteValue.triangle }
    }
}
