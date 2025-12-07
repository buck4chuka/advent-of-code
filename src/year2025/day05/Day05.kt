package year2025.day05

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val (idRangesStr, ids) = input.filter { it.trim().isNotEmpty() }.partition { it.contains('-') }
        val idRanges = idRangesStr
            .map { it.split('-').map { i -> i.toLong() } }
            .map { (start, end) -> start..end }
        return ids.map { it.toLong() }.count { id -> idRanges.any { it.contains(id) } }
    }

    fun part2(input: List<String>): Long = input.filter { it.contains('-') }
            .map { it.split('-').map { i -> i.toLong() } }
            .map { (start, end) -> start to end }
            .sortedBy { it.first }
            .fold(emptyList<Pair<Long, Long>>()) { acc, (start, end) ->
                if (acc.isEmpty() || start > acc.last().second) return@fold acc + (start to end)
                acc.dropLast(1) + (acc.last().first to maxOf(acc.last().second, end))
            }.sumOf { it.second - it.first + 1}


    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("year2025/day05/test")
    part1(testInput)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("year2025/day05/input")
    part1(input).println()
    part2(input).println()
}
