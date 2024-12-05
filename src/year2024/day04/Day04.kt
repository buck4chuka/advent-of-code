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

fun buildWord(input: List<String>, coord: Pair<Int, Int>, direction: Pair<Int, Int>): String {
    val (dx, dy) = direction
    val getChar: (Int) -> Char? =
        { step -> input.getOrNull(coord.first + step * dx)?.getOrNull(coord.second + step * dy) }
    return (0 until 4).mapNotNull(getChar).joinToString("")
}

fun letterCoordinates(input: List<String>, letter: Char): List<Pair<Int, Int>> {
    return input.indices.cartesianProduct(input[0].indices)
        .filter { (row, col) -> input[row][col] == letter }
}

fun main() {
    fun part1(input: List<String>): Int {
        val xmasAllDir: (Pair<Int, Int>) -> List<String> =
            { directions().map { dir -> buildWord(input, it, dir) }.filter { it == "XMAS" } }
        return letterCoordinates(input, 'X').flatMap(xmasAllDir).count()
    }

    fun part2(input: List<String>): Int {
        return letterCoordinates(input, 'A').count { isValid(input, it) }
    }

    val input = readInput("year2024/day04/Day04")
    part1(input).println()
    part2(input).println()
}
