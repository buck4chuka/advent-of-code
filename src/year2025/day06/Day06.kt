package year2025.day06

import println
import readInput
import readInput1

fun resolveRow(row: List<String>): Long {
    val operand = row.first()
    val restElements = row.drop(1).map { it.toLong() }
    return applyOperand(operand[0], restElements)
}

fun applyOperand(operand: Char, numsSoFar: List<Long>): Long {
   return when (operand) {
        '*' -> numsSoFar.filter { it != 0L }.reduce { acc, l -> acc * l }
        '+' -> numsSoFar.sum()
        else -> 0L
    }
}

fun main() {
    fun part1(input: List<String>): Long {
        val cleanedInput = input.map { it.split(Regex("\\s+")) }.map { it.filter { s -> s.isNotEmpty() } }
        val transformed =
            cleanedInput[0].indices.map { cleanedInput.indices.map { r -> cleanedInput[r][it] }.reversed() }
        return transformed.sumOf { resolveRow(it) }
    }

    fun part2(input: List<String>): Long {
        val longestLine = input.maxOf { it.length }
        val paddedInput = input.map { it.padEnd(longestLine) }

        var totalNumbers = 0L
        var numsSoFar = mutableListOf<Long>()

        for (col in paddedInput[0].indices.reversed()) {
            var num = 0L
            for (row in paddedInput.indices) {
                val c = paddedInput[row][col]
                if (c in setOf('*', '+')) {
                    totalNumbers += applyOperand(c, numsSoFar + num)
                    numsSoFar = mutableListOf()
                    num = 0L
                    continue
                }
                c.digitToIntOrNull()?.let { num = num * 10 + it }
            }
            numsSoFar.add(num)
        }
        return totalNumbers
    }

    val testInput = readInput("year2025/day06/test")
    val testInput1 = readInput1("year2025/day06/test")
//    check(part1(testInput) == 4277556L)
//    part2(testInput1).println()


//    // Read the input from the `src/Day01.txt` file.
    val input = readInput("year2025/day06/input")
    part1(input).println()
    part2(readInput1("year2025/day06/input")).println()
}
