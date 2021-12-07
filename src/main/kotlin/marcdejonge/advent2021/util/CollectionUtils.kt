package marcdejonge.advent2021.util

fun <T> Collection<T>.allPairs(): Sequence<Pair<T, T>> = asSequence().allPairs()

fun <T> Sequence<T>.allPairs(): Sequence<Pair<T, T>> = flatMapIndexed { ix, first ->
    drop(ix + 1).map { first to it }
}
