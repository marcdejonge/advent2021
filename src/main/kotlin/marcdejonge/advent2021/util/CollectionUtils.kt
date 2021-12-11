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
