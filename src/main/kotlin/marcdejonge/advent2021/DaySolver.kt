package marcdejonge.advent2021

import kotlin.system.measureTimeMillis

abstract class DaySolver(val day: Int) {
    private val url by lazy {
        this::class.java.classLoader.getResource("day$day.txt") ?: error("Could not load file day$day.txt")
    }

    val input: List<String> by lazy {
        url.openStream().bufferedReader().lines().toList()
    }

    val solutionPart1 by lazy { calcPart1() }
    val solutionPart2 by lazy { calcPart2() }

    open fun calcPart1(): Number? = null

    open fun calcPart2(): Number? = null

    fun printSolution() {
        println("$this:")
        val time = measureTimeMillis {
            println("    Part 1: ${solutionPart1 ?: "no solution found"}")
            println("    Part 2: ${solutionPart2 ?: "no solution found"}")
        }
        println("Calculated in $time milliseconds")
    }

    override fun toString() = "Day $day"
}
