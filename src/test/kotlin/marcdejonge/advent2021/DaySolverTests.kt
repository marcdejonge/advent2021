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
            Arguments.of(Day1, 7, 5),
            Arguments.of(Day2, 150L, 900L),
            Arguments.of(Day3, 198, 230),
            Arguments.of(Day4, 4512, 1924),
            Arguments.of(Day5, 5, 12),
            Arguments.of(Day6, 5934L, 26984457539L),
            Arguments.of(Day7, 37, 168),
            Arguments.of(Day8, 26, 61229),
            Arguments.of(Day9, 15, 1134),
            Arguments.of(Day10, 26397L, 288957L),
            Arguments.of(Day11, 1656, 195),
            Arguments.of(Day12, 226, 3509),
            Arguments.of(Day13, 17, """
██████████
██      ██
██      ██
██      ██
██████████"""),
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
