package year2025.day03

import println
import readInput


fun findHighest(digitSize: Int): (String) -> Long =
    { input ->
        val initial = input.substring(0 until digitSize).toMutableList()
        val remaining = input.substring(digitSize)

        for (char in remaining) {
            initial.add(char)
            for (i in 0 until initial.size - 1) {
                if (initial[i] < initial[i + 1]) {
                    initial.removeAt(i)
                    break
                }
            }
            if (initial.size > digitSize) initial.removeLast()
        }
        initial.joinToString("").toLong()
    }

fun main() {
    fun part1(input: List<String>): Long = input.sumOf { findHighest(2)(it) }
    fun part2(input: List<String>): Long = input.sumOf { findHighest(12)(it) }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("year2025/day03/test")
    check( part1(testInput) == 357L)
    check( part2(testInput) == 3121910778619)


    val input = readInput("year2025/day03/input")
     part1(input).println()
    part2(input).println()
}
