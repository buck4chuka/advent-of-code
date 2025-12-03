package year2025.day02

import println
import readInput


fun splitToRange(input: String): LongRange {
    val (start, end) = input.split("-")
    return start.toLong()..end.toLong()
}

fun filterMissingNumber(range: LongRange): List<Long> {
    return range.filter {
        val numString = it.toString()
        val halfNum = numString.length.div(2)
        numString.length % 2 == 0 && numString.substring(0 until halfNum) == numString.substring(halfNum)
    }
}

fun filterMissingNumber2(range: LongRange): List<Long> {
    return range.filter { num ->
        val numString = num.toString()
        (1 .. numString.length / 2).any { numString.chunked(it).distinct().size == 1 }
    }
}

fun main() {
    fun part1(input: String): Long {
        return input
            .split(",")
            .map { splitToRange(it) }
            .flatMap { filterMissingNumber(it) }
            .sum()
    }

    fun part2(input: String): Long {
       return input.split(",")
            .map { splitToRange(it) }
            .flatMap { filterMissingNumber2(it) }
            .sum()
    }

    val testInput = readInput("year2025/day02/example")[0]
    check(part1(testInput) == 1227775554L )
    check(part2(testInput) == 4174379265L)


     val input = readInput("year2025/day02/Day02")[0]
    "part1: %d".format( part1(input)).println()
    "part2: %d".format( part2(input)).println()
}
