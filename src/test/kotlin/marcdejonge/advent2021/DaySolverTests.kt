package marcdejonge.advent2021

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream
import kotlin.test.assertEquals

class DaySolverTests {
    class ExpectedResults : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext): Stream<Arguments> = Stream.of(
            Arguments.of(Day1(), 7, 5),
            Arguments.of(Day2(), 150L, 900L),
        )
    }

    @ParameterizedTest
    @ArgumentsSource(ExpectedResults::class)
    fun `verify day solver`(solver: DaySolver, expectedPart1: Any?, expectedPart2: Any?) {
        assertEquals(
            expectedPart1,
            solver.solutionPart1,
            "Solving $solver.solutionPart1 did not return the expected result"
        )
        assertEquals(
            expectedPart2,
            solver.solutionPart2,
            "Solving $solver.solutionPart2 did not return the expected result"
        )
    }
}
