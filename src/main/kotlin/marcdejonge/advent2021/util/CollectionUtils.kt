package marcdejonge.advent2021.util

fun <T> Collection<T>.allPairs(): Sequence<Pair<T, T>> = asSequence().allPairs()

fun <T> Sequence<T>.allPairs(): Sequence<Pair<T, T>> = flatMapIndexed { ix, first ->
    drop(ix + 1).map { first to it }
}

fun <T : Comparable<T>> Collection<T>.middle(): T = sorted().let { sorted ->
    sorted[sorted.size / 2]
}

fun <T : Comparable<T>> Sequence<T>.middle(): T = toList().sorted().let { sorted ->
    sorted[sorted.size / 2]
}

fun <T> Sequence<T>.sumOfNotNull(block: (T) -> Long?) = mapNotNull(block).sum()

fun <T> T.iterate(step: (T) -> T): Sequence<T> = generateSequence(this) { step(it) }

fun <T> Sequence<T>.findFirstUnchanged() = zipWithNext().first { it.first == it.second }.first

fun <K, V> Iterable<Pair<K, V>>.toMapGroup() = groupBy(Pair<K, V>::first, Pair<K, V>::second)

fun <T> Sequence<T>.contains(predicate: (T) -> Boolean) = find(predicate) != null
