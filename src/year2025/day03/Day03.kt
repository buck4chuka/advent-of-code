package year2025.day03

import println
import readInput
import kotlin.text.toList



fun findHighest(digitSize: Int): (String) -> Long =
    { input ->
        val (initial, remaining) = input.take(digitSize) to input.drop(digitSize)
        remaining.fold(initial.toList()) { digits, char ->
            val currDigits = digits + char
            val idx = currDigits.indices.firstOrNull { (it < currDigits.lastIndex && currDigits[it] < currDigits[it + 1]) || it == currDigits.lastIndex}
            currDigits.filterIndexed { i, _ -> i != idx }
        }.joinToString("").toLong()
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
