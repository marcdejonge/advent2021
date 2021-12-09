package marcdejonge.advent2021.util

@JvmInline
value class IntMatrix(private val array: Array<IntArray>) {
    constructor(list: List<List<Int>>) : this(list.map { it.toIntArray() }.toTypedArray())

    operator fun get(index: Index2D) = array[index.y][index.x]

    fun indexed() = sequence {
        array.indices.forEach { y -> array[y].indices.forEach { x -> yield(Index2D(x, y).withValue()) } }
    }

    private fun isInRange(index: Index2D) = index.let { (x, y) -> y in array.indices && x in array[y].indices }

    fun neighbours(index: Index2D) = sequenceOf(index.up, index.down, index.left, index.right)
        .filter { isInRange(it) }.map { it.withValue() }

    private fun Index2D.withValue() = IndexedIntValue2D(this, this@IntMatrix[this])
}

data class Index2D(val x: Int, val y: Int) {
    val up get() = Index2D(x, y - 1)
    val down get() = Index2D(x, y + 1)
    val left get() = Index2D(x - 1, y)
    val right get() = Index2D(x + 1, y)
}

data class IndexedIntValue2D(val index: Index2D, val value: Int)
