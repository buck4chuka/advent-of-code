package year2024.day04

import cartesianProduct
import println
import readInput

fun isValid(input: List<String>, coord: Pair<Int, Int>): Boolean {
    val (row, col) = coord
    if (row - 1 in input.indices
        && row + 1 in input.indices
        && col - 1 in input[row].indices
        && col + 1 in input[row].indices
    ) {
        val sm = setOf('S','M')
        val diag1 = setOf(input[row - 1][col - 1], input[row + 1][col + 1])
        val diag2 = setOf(input[row - 1][col + 1], input[row + 1][col - 1])
        return  diag1 == sm && diag2 == sm
    }
    return false
}

fun buildWord(input: List<String>, row: Int, col: Int, direction: Pair<Int, Int>): String {
    return (0 until 4).mapNotNull { step ->
            val i = row + step * direction.first
            val j = col + step * direction.second
            if (i in input.indices && j in input[i].indices) input[i][j] else null
        }
        .joinToString("")
}

fun directions(): List<Pair<Int, Int>> {
    val range = (-1..1)
    return range.cartesianProduct(range).filter { it != Pair(0, 0) }
}

fun findXmasAllDirections(input: List<String>, coord: Pair<Int, Int>): List<String> {
    return directions().map { buildWord(input, coord.first, coord.second, it) }.filter { it == "XMAS" }
}

fun letterCoordinates(input: List<String>, letter: Char): List<Pair<Int, Int>> {
    return input.indices.cartesianProduct(input[0].indices).filter { (row, col) -> input[row][col] == letter }
}


fun main() {
    fun part1(input: List<String>): Int {
        return letterCoordinates(input, 'X').flatMap { findXmasAllDirections(input, it) }.count()
    }

    fun part2(input: List<String>): Int {
        return letterCoordinates(input, 'A').count { isValid(input, it) }
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("year2024/day04/Day04")
    part1(input).println()
    part2(input).println()
}
