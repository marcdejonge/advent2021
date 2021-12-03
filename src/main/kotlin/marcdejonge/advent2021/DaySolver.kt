package marcdejonge.advent2021

abstract class DaySolver(val day: Int) {
    private val url by lazy {
        this::class.java.classLoader.getResource("day$day.txt") ?: error("Could not load file day$day.txt")
    }

    val input by lazy {
        url.openStream().bufferedReader().useLines { lines -> lines.filter { it.isNotBlank() }.toList() }
    }

    val solutionPart1 by lazy { calcPart1() }
    val solutionPart2 by lazy { calcPart2() }

    open fun calcPart1(): Number? = null

    open fun calcPart2(): Number? = null

    fun printSolution() {
        println("$this:")
        println("    Part 1: ${solutionPart1 ?: "no solution found"}")
        println("    Part 2: ${solutionPart2 ?: "no solution found"}")
    }

    override fun toString() = "Day $day"
}
