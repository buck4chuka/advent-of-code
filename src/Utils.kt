import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun <T, U> Iterable<T>.cartesianProduct(other: Iterable<U>): List<Pair<T, U>> =
    this.flatMap { other.map { b -> it to b } }

val directions = (-1..1).cartesianProduct((-1..1)).filter { it != Pair(0, 0) }

data class Point2D(val x: Int, val y: Int) {
    operator fun minus(other: Point2D): Point2D = Point2D(x - other.x, y - other.y)
    operator fun plus(other: Point2D): Point2D = Point2D(x + other.x, y + other.y)
}

val nsewDirections = listOf(Pair(0, 1), Pair(0, -1), Pair(1, 0), Pair(-1, 0))

enum class Direction(val r: Int, val c: Int) {
    EAST(0, 1),
    WEST(0, -1),
    NORTH(1, 0),
    SOUTH(-1, 0)
}

operator fun Pair<Int, Int>.plus(other: Direction): Pair<Int, Int> {
    return first + other.r to second + other.c
}
