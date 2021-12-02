package marcdejonge.advent2021.util

import marcdejonge.advent2021.DaySolver

class Input(private val solver: DaySolver) {
    val url by lazy {
        solver::class.java.classLoader.getResource("day${solver.day}.txt") ?: error("Could not load file day${solver.day}.txt")
    }

    inline fun <T> parse(crossinline block: List<String>.() -> T): T =
        url.openStream().bufferedReader().useLines { lines -> block(lines.filter { it.isNotBlank() }.toList()) }
}
