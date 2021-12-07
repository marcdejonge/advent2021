package marcdejonge.advent2021.util

class Memoize<IN, OUT>(val calculate: (IN, (IN) -> OUT) -> OUT) : (IN) -> OUT {
    private val cache = hashMapOf<IN, OUT>()

    override fun invoke(key: IN): OUT = cache.getOrPut(key) { calculate(key, this) }
}
