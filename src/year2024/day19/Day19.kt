package year2024.day19

import println
import readInput

fun part1(designs: List<String>, patterns: Set<String>): Int {
    val memo: MutableSet<String> = mutableSetOf()
    fun isPossible(design: String): Boolean {
        if (design.isEmpty() || design in memo) return true
        val result = design.indices.map(Int::inc)
            .filter { design.take(it) in patterns }
            .any { isPossible(design.drop(it)) }
        if (result) memo.add(design)
        return result
    }
    return designs.count(::isPossible)
}

fun part2(designs: List<String>, patterns: Set<String>): Long {
    val memo: MutableMap<String, Long> = mutableMapOf()
    fun countPossible(design: String): Long {
        if (design.isEmpty()) return 1
        memo[design]?.let { return it }
        val result = (1..design.length)
            .filter { design.take(it) in patterns }
            .sumOf { countPossible(design.drop(it)) }
        memo[design] = result
        return result
    }
    return designs.map(::countPossible).sum()
}

fun main() {
    val input = readInput("year2024/day19/Day19")
    val patterns = input.first().split(',').map { it.trim() }.toSet()
    val possibleDesigns = input.drop(2)
    part1(possibleDesigns, patterns).println()
    part2(possibleDesigns,patterns).println()
}

