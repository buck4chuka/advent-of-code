package year2024.day07

import println
import readInput

fun parseLine(line: String): Pair<Long, List<Long>> {
    val (result, rest) = line.split(": ")
    return Pair(result.toLong(), rest.split("\\s+".toRegex()).map { it.toLong() })
}

fun allPermutations(
    input: List<Long>,
    target: Long,
    operations: List<(Long, Long) -> Long>
): Set<Long> {
    if (input.isEmpty()) {
        return emptySet()
    }
    val results = mutableSetOf<Long>()

    fun dfs(variables: List<Long>, curr: Long) {
        if (variables.isEmpty()) {
            results.add(curr)
            return
        }
        operations
            .map { op -> op(curr, variables.first()) }
            .filter { it <= target }
            .forEach { dfs(variables.drop(1), it) }
    }

    dfs(input.drop(1), input.first())
    return results
}

fun part1(input: List<String>): Long {
    val ops = listOf<(Long, Long) -> Long>({a,b -> a + b}, { a,b -> a * b })
    return input.map { parseLine(it) }
        .filter { (result, variables) -> allPermutations(variables, result,ops).contains(result) }
        .sumOf { it.first }
}

fun part2(input: List<String>): Long {
    val ops = listOf<(Long, Long) -> Long>({a,b -> a + b}, { a,b -> a * b } , {a,b -> (a.toString() + b.toString()).toLong() })
    return input.map { parseLine(it) }
        .filter { (result, variables) -> allPermutations(variables, result,ops).contains(result) }
        .sumOf { it.first }
}

fun main() {
    val input = readInput("year2024/day07/Day07")
    part1(input).println()
    part2(input).println()
}

