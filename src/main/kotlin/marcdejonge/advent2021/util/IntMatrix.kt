package marcdejonge.advent2021.util

@JvmInline
value class IntMatrix(private val array: Array<IntArray>) {
    constructor(list: List<List<Int>>) : this(list.map { it.toIntArray() }.toTypedArray())

    operator fun get(index: Index2D) = array[index.y][index.x]

    fun values() = indexed().map { (_, v) -> v }

    fun indexed() = sequence {
        array.indices.forEach { y -> array[y].indices.forEach { x -> yield(Index2D(x, y).withValue()) } }
    }

    private fun isInRange(index: Index2D) = index.let { (x, y) -> y in array.indices && x in array[y].indices }

    fun neighbours(index: Index2D) = sequenceOf(
        index.up, index.down, index.left, index.right
    ).filter { isInRange(it) }.map { it.withValue() }

    fun neighboursDiag(index: Index2D) = sequenceOf(
        index.up, index.down, index.left, index.right,
        index.upLeft, index.upRight, index.downLeft, index.downRight
    ).filter { isInRange(it) }.map { it.withValue() }

    fun map(block: (IndexedIntValue2D) -> Int) = IntMatrix(
        array.mapIndexed { y, row ->
            row.mapIndexed { x, v ->
                block(IndexedIntValue2D(Index2D(x, y), v))
            }
        }
    )

    fun applyValues(changes: Sequence<IndexedIntValue2D>) = array.copyOf().let { newArray ->
        changes.forEach { (ix, v) ->
            if (newArray[ix.y] === array[ix.y]) newArray[ix.y] = array[ix.y].copyOf()
            newArray[ix.y][ix.x] = v
        }
        IntMatrix(newArray)
    }

    private fun Index2D.withValue() = withValue(this@IntMatrix[this])

    fun toString(block: (Int) -> String) = array.joinToString("\n", postfix = "\n") { line ->
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
