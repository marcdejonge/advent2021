package marcdejonge.advent2021

import marcdejonge.advent2021.util.Input

abstract class DaySolver(val day: Int) {
    internal val input: Input
        get() = Input(this)
    val solutionPart1 by lazy { calcPart1() }
    val solutionPart2 by lazy { calcPart2() }

    open fun calcPart1(): Long? = null

    open fun calcPart2(): Long? = null

    fun printSolution() {
        println("$this:")
        println("    Part 1: ${solutionPart1 ?: "no solution found"}")
        println("    Part 2: ${solutionPart2 ?: "no solution found"}")
    }

    override fun toString() = "Day $day"
}
