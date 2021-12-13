package marcdejonge.advent2021.util

class IntMatrix(
    private val array: IntArray,
    private val lineLength: Int,
) {
    constructor(list: List<List<Int>>) : this(list.flatten().toIntArray(), list.first().size)

    constructor(
        lineLength: Int,
        lines: Int,
        block: (IntVector2) -> Int = { 0 }
    ) : this(IntArray(lineLength * lines) { ix ->
        block(IntVector2(ix % lineLength, ix / lineLength))
    }, lineLength)

    private inline val IntVector2.arrayIx get() = x + y * lineLength
    private inline val Int.toIntVector2 get() = IntVector2(this % lineLength, this / lineLength)

    operator fun get(index: IntVector2) = array[index.arrayIx]

    private operator fun set(index: IntVector2, value: Int) {
        array[index.arrayIx] = value
    }

    fun values() = array.asSequence()

    fun indexed() = sequence {
        array.indices.forEach { arrayIx -> yield(arrayIx.toIntVector2.withValue(array[arrayIx])) }
    }

    private fun isInRange(index: IntVector2) = index.let { (x, y) ->
        (x in 0 until lineLength) && (y * lineLength in array.indices)
    }

    /** Gives a sequence of all the neighbouring values, only horizontal and vertical */
    fun directNeighbours(index: IntVector2) = sequenceOf(
        index.up, index.down, index.left, index.right
    ).filter { isInRange(it) }.map { it.withValue() }

    /** Gives a sequence of all the neighbouring values, also diagonal */
    fun allNeighbours(index: IntVector2) = sequenceOf(
        index.up, index.down, index.left, index.right,
        index.upLeft, index.upRight, index.downLeft, index.downRight
    ).filter { isInRange(it) }.map { it.withValue() }

    fun map(block: (IndexedIntValue2D) -> Int) = IntMatrix(
        array.copyOf().also {
            it.indices.forEach { ix -> it[ix] = block(IndexedIntValue2D(ix.toIntVector2, it[ix])) }
        }, lineLength
    )

    operator fun plus(changes: Sequence<IndexedIntValue2D>) = IntMatrix(
        array.copyOf().also { changes.forEach { (ix, v) -> it[ix.arrayIx] += v } }, lineLength
    )

    operator fun plus(changes: Iterable<IndexedIntValue2D>) = IntMatrix(
        array.copyOf().also { changes.forEach { (ix, v) -> it[ix.arrayIx] += v } }, lineLength
    )

    private fun IntVector2.withValue() = withValue(this@IntMatrix[this])

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

operator fun Iterable<IndexedIntValue2D>.plus(matrix: IntMatrix) = matrix.plus(this.asSequence())

fun IntVector2.withValue(value: Int) = IndexedIntValue2D(this, value)
val IntVector2.up get() = IntVector2(x, y - 1)
val IntVector2.down get() = IntVector2(x, y + 1)
val IntVector2.left get() = IntVector2(x - 1, y)
val IntVector2.right get() = IntVector2(x + 1, y)
val IntVector2.upLeft get() = IntVector2(x - 1, y - 1)
val IntVector2.upRight get() = IntVector2(x + 1, y - 1)
val IntVector2.downLeft get() = IntVector2(x - 1, y + 1)
val IntVector2.downRight get() = IntVector2(x + 1, y + 1)

data class IndexedIntValue2D(val index: IntVector2, val value: Int)
