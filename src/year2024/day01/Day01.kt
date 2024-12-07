package year2024.day01

import println
import readInput
import kotlin.math.abs

fun main() {
    fun toLeftRight(input: List<String>): Pair<List<Int>, List<Int>> {
        return input.foldRight(Pair(listOf(), listOf())) { line, acc ->
            val (left, right) = line.split("\\s+".toRegex()).map { it.toInt() }
            Pair(acc.first + left, acc.second + right)
        }
    }

    fun part1(input: List<String>): Int {
        val (left, right) = toLeftRight(input)
        return left.sorted().zip(right.sorted()).sumOf { abs(it.first - it.second) }
    }

    fun part2(input: List<String>): Int {
        val (left, right) = toLeftRight(input)
        val rFreqCount = right.groupingBy { it }.eachCount()
        return left.sumOf { (rFreqCount[it] ?: 0) * it }
    }


    // Read the input from the `src/Day01.txt` file.
    val input = readInput("year2024/day01/Day01")
    part1(input).println()
    part2(input).println()
}
