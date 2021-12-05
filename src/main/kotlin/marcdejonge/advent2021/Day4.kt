package marcdejonge.advent2021

fun main() = Day4.printSolution()

object Day4 : DaySolver(4) {
    private val drawnNumbers = input.first().split(",").map { it.toInt() }
    private val cards = input.drop(1)
        .map { line -> line.split(" ").filter { it.isNotBlank() }.map { it.toInt() } }
        .fold(ArrayList<MutableList<List<Int>>>()) { accum, list ->
            if (list.isEmpty()) accum.apply { add(ArrayList()) }
            else accum.apply { last().add(list) }
        }.map { Card(it) }

    data class Step(val card: Card, val lastNumber: Int = 0, val numbersTaken: Int = 0) : Comparable<Step> {
        val score: Int get() = card.numbers.sumOf { if (it > 0) it else 0 } * lastNumber
        fun draw(number: Int) = Step(card.draw(number), number, numbersTaken + 1)
        override fun compareTo(other: Step) = compareValuesBy(this, other, Step::numbersTaken)
    }

    data class Card(val numbers: List<Int>, val lineSize: Int) {
        constructor(input: List<List<Int>>) : this(input.flatten(), input.first().size)

        fun draw(number: Int) = copy(numbers = numbers.map { if (it == number) -1 else it })

        private fun isBingo() =
            (numbers.indices step lineSize).any { rowIx ->
                (rowIx until (rowIx + lineSize)).all { ix -> numbers[ix] < 0 }
            } or (0 until lineSize).any { columnIx ->
                (columnIx until numbers.size step lineSize).all { ix -> numbers[ix] < 0 }
            }

        private fun applyDraws(drawNumbers: List<Int>) = drawNumbers.asSequence()
            .runningFold(Step(this)) { currentStep, number -> currentStep.draw(number) }

        fun findFirstBingo(drawnNumbers: List<Int>) = applyDraws(drawnNumbers).first { it.card.isBingo() }
    }

    override fun calcPart1() = cards.minOf { it.findFirstBingo(drawnNumbers) }.score

    override fun calcPart2() = cards.maxOf { it.findFirstBingo(drawnNumbers) }.score
}
