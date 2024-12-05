package year2024.day04

import cartesianProduct
import directions
import println
import readInput

fun isValid(input: List<String>, coord: Pair<Int, Int>): Boolean {
    val (row, col) = coord
    fun char(deltaX: Int, deltaY: Int) = input.getOrNull(row + deltaX)?.getOrNull(col + deltaY)
    val diag1 = setOf(char(-1, -1), char(1, 1))
    val diag2 = setOf(char(-1, 1), char(1, -1))
    return diag1 == setOf('S', 'M') && diag2 == setOf('S', 'M')
}

fun maybeXmas(input: List<String>, coord: Pair<Int, Int>, direction: Pair<Int, Int>): String? {
    val (dx, dy) = direction
    fun getChar(step: Int): Char? =
        input.getOrNull(coord.first + step * dx)?.getOrNull(coord.second + step * dy)

    val word = (0 until 4).mapNotNull(::getChar).joinToString("")
    return if (word == "XMAS") word else null
}

fun letterCoordinates(input: List<String>, letter: Char): List<Pair<Int, Int>> {
    return input.indices.cartesianProduct(input[0].indices)
        .filter { (row, col) -> input[row][col] == letter }
}

fun main() {
    fun part1(input: List<String>): Int = letterCoordinates(input, 'X')
        .flatMap { coord -> directions.mapNotNull { maybeXmas(input, coord, it) } }
        .count()

    fun part2(input: List<String>): Int =
        letterCoordinates(input, 'A').count { isValid(input, it) }

    val input = readInput("year2024/day04/Day04")
    part1(input).println()
    part2(input).println()
}
