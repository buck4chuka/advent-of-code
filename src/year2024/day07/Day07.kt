package year2024.day07

import println
import readInput

fun parseLine(line: String): Pair<Long, List<Long>> {
    val (result, rest) = line.split(": ")
    return Pair(result.toLong(), rest.split("\\s+".toRegex()).map { it.toLong() })
}

fun allPermutations(
    target: Long,
    input: List<Long>,
    extraOps: ((Long, Long) -> Long)? = null,
): Set<Long> {
    if (input.isEmpty()) return emptySet()
    val ops = listOf<(Long, Long) -> Long>({ a, b -> a + b }, { a, b -> a * b }) + extraOps
    val results = mutableSetOf<Long>()
    fun recur(remaining: List<Long>, current: Long) {
        if (remaining.isEmpty()) {
            results.add(current)
            return
        }
        ops.mapNotNull { op -> op?.let { it(current, remaining.first()) } }
            .filter { it <= target }
            .forEach { recur(remaining.drop(1), it) }
    }
    recur(input.drop(1), input.first())
    return results
}

fun part1(input: List<String>): Long {
    return input
        .map { parseLine(it) }
        .filter { (result, variables) -> allPermutations(result, variables).contains(result) }
        .sumOf { it.first }
}

fun part2(input: List<String>): Long {
    val concatOp: (Long, Long) -> Long = { a, b -> (a.toString() + b.toString()).toLong() }
    return input
        .map { parseLine(it) }
        .filter { (result, variables) ->
            allPermutations(result, variables, concatOp).contains(result)
        }
        .sumOf { it.first }
}

fun main() {
    val input = readInput("year2024/day07/Day07")
    part1(input).println()
    part2(input).println()
}
