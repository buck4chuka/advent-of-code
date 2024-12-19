package year2024.day19

import println
import readInput

fun part1(designs: List<String>, patterns: Set<String>): Int {
    fun isPossible(design: String): Boolean {
        if (design.isEmpty()) return true
        return (1..design.length)
            .any { design.take(it) in patterns && isPossible(design.drop(it))  }
    }
    return designs.count(::isPossible)
}

fun part2(designs: List<String>, patterns: Set<String>): Long {
    val memo: MutableMap<String, Long> = mutableMapOf()
    fun countPossible(design: String): Long {
        if (design.isEmpty()) return 1
        memo[design]?.let { return it }
        memo[design] = (1..design.length)
            .filter { design.take(it) in patterns }
            .sumOf { countPossible(design.drop(it)) }
        return memo[design]!!
    }
    return designs.sumOf(::countPossible)
}

fun main() {
    val input = readInput("year2024/day19/Day19")
    val patterns = input.first().split(',').map { it.trim() }.toSet()
    val possibleDesigns = input.drop(2)
    part1(possibleDesigns, patterns).println()
    part2(possibleDesigns, patterns).println()
}
