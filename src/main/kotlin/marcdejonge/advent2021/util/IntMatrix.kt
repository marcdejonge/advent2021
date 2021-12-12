package marcdejonge.advent2021.util

import marcdejonge.advent2021.Day11

fun main() {
    println(Day11.startField.map { (_, v) -> v + 1 })
}

class IntMatrix(
    private val array: IntArray,
    private val lineLength: Int,
) {
    constructor(list: List<List<Int>>) : this(list.flatten().toIntArray(), list.first().size)

    private inline val Index2D.arrayIx get() = x + y * lineLength
    private inline val Int.toIndex2D get() = Index2D(this % lineLength, this / lineLength)

    operator fun get(index: Index2D) = array[index.arrayIx]

    private operator fun set(index: Index2D, value: Int) {
        array[index.arrayIx] = value
    }

    fun values() = array.asSequence()

    fun indexed() = sequence {
        array.indices.forEach { arrayIx -> yield(arrayIx.toIndex2D.withValue(array[arrayIx])) }
    }

    private fun isInRange(index: Index2D) = index.let { (x, y) ->
        (x in 0 until lineLength) && (y * lineLength in array.indices)
    }

    /** Gives a sequence of all the neighbouring values, only horizontal and vertical */
    fun directNeighbours(index: Index2D) = sequenceOf(
        index.up, index.down, index.left, index.right
    ).filter { isInRange(it) }.map { it.withValue() }

    /** Gives a sequence of all the neighbouring values, also diagonal */
    fun allNeighbours(index: Index2D) = sequenceOf(
        index.up, index.down, index.left, index.right,
        index.upLeft, index.upRight, index.downLeft, index.downRight
    ).filter { isInRange(it) }.map { it.withValue() }

    fun map(block: (IndexedIntValue2D) -> Int) = IntMatrix(
        array.copyOf().also {
            it.indices.forEach { ix -> it[ix] = block(IndexedIntValue2D(ix.toIndex2D, it[ix])) }
        }, lineLength
    )

    fun applyValues(changes: Sequence<IndexedIntValue2D>) = IntMatrix(
        array.copyOf().also {
            changes.forEach { (ix, v) ->
                it[ix.arrayIx] = v
            }
        }, lineLength
    )

    private fun Index2D.withValue() = withValue(this@IntMatrix[this])

    override fun equals(other: Any?) = (other as? IntMatrix)?.let {
        array.contentEquals(it.array) && lineLength == it.lineLength
    } ?: false

    override fun hashCode() = array.contentHashCode() * 37 + lineLength

    fun toString(block: (Int) -> String) = array.asSequence().chunked(lineLength)
        .joinToString("\n", postfix = "\n") { line ->
            line.joinToString("") { block(it) }
        }

    override fun toString() = toString(Int::toString)
}

fun Iterable<IndexedIntValue2D>.applyTo(matrix: IntMatrix) = matrix.applyValues(this.asSequence())

data class Index2D(val x: Int = 0, val y: Int = 0) {
    val up get() = Index2D(x, y - 1)
    val down get() = Index2D(x, y + 1)
    val left get() = Index2D(x - 1, y)
    val right get() = Index2D(x + 1, y)

    val upLeft get() = Index2D(x - 1, y - 1)
    val upRight get() = Index2D(x + 1, y - 1)
    val downLeft get() = Index2D(x - 1, y + 1)
    val downRight get() = Index2D(x + 1, y + 1)

    fun withValue(value: Int) = IndexedIntValue2D(this, value)
}

data class IndexedIntValue2D(val index: Index2D, val value: Int)
