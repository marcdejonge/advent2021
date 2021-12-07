package marcdejonge.advent2021.util

import kotlin.math.sqrt

data class Vector2(val x: Double, val y: Double) {
    operator fun plus(other: Vector2) = Vector2(x + other.x, y + other.y)
    operator fun minus(other: Vector2) = Vector2(x - other.x, y - other.y)
    operator fun div(scale: Double) = Vector2(x / scale, y / scale)
    operator fun times(scale: Double) = Vector2(x * scale, y * scale)

    fun cross(other: Vector2) = x * other.y - y * other.x
    fun dot(other: Vector2) = x * other.x + y * other.y

    fun size() = sqrt(dot(this))

    override fun toString() = "($x,$y)"
}

data class IntVector2(val x: Int, val y: Int) {
    operator fun plus(other: IntVector2) = IntVector2(x + other.x, y + other.y)
    operator fun minus(other: IntVector2) = IntVector2(x - other.x, y - other.y)
    operator fun div(scale: Double) = Vector2(x / scale, y / scale)
    operator fun times(scale: Double) = Vector2(x * scale, y * scale)

    fun cross(other: IntVector2) = x * other.y - y * other.x
    fun dot(other: IntVector2) = x * other.x + y * other.y
    fun dot(other: Vector2) = x * other.x + y * other.y

    fun size() = sqrt(dot(this).toDouble())

    override fun toString() = "($x,$y)"

    // Extensions for easy creation of other objects
    operator fun rangeTo(end: IntVector2) = LineSegment(this, end)
}

infix fun Int.v(y: Int) = IntVector2(this, y)
