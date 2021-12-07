package marcdejonge.advent2021

import marcdejonge.advent2021.util.LineSegment
import marcdejonge.advent2021.util.v

fun main() = Day5.printSolution()

object Day5 : DaySolver(5) {
    private val linePattern = """(\d+),(\d+) -> (\d+),(\d+)""".toRegex()
    private val lineSegments = input.map {
        linePattern.find(it)!!.destructured.let { (x1, y1, x2, y2) ->
            (x1.toInt() v y1.toInt())..(x2.toInt() v y2.toInt())
        }
    }

    private fun createMap() = Array(1000) { IntArray(1000) { 0 } }
    private fun LineSegment.drawTo(map: Array<IntArray>) = indices.forEach { (x, y) -> map[x][y]++ }
    private fun mapWith(lines: Collection<LineSegment>) = createMap().also { map ->
        lines.forEach { it.drawTo(map) }
    }
    private fun Array<IntArray>.countDuplicateCells() = sumOf { it.count { value -> value > 1 } }

    override fun calcPart1() = mapWith(lineSegments.filter { it.isStraight }).countDuplicateCells()
    override fun calcPart2() = mapWith(lineSegments).countDuplicateCells()
}
