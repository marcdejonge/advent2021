package marcdejonge.advent2021.util

import com.diogonunes.jcolor.Ansi
import com.diogonunes.jcolor.Attribute
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

fun Int.toColoredString(min: Int = 0, max: Int = 9): String {
    val v = max(min(this, max), min)
    return Ansi.colorize(
        v.toString(),
        Attribute.TEXT_COLOR((235 + (v - min) * (20.0 / (max - min))).roundToInt()),
        Attribute.BLACK_BACK(),
    )
}
