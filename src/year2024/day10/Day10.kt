package year2024.day10

import nsewDirections
import println
import readInput

private fun List<String>.toRowColIndices(): List<Pair<Int, Int>> {
    return this.indices.flatMap { r -> this.first().indices.map { Pair(r, it) } }
}

private fun List<String>.inBounds(coord: Pair<Int, Int>): Boolean {
    return coord.first in this.indices && coord.second in this.first().indices
}

private fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
    return first + other.first to second + other.second
}

private fun List<String>.get(coord: Pair<Int, Int>): Char? {
   return this.getOrNull(coord.first)?.getOrNull(coord.second)
}

fun part1(input: List<String>): Int {
    return input.toRowColIndices().filter { input[it.first][it.second] == '0' }
        .sumOf { findHikingTrail(input, it) }
}

fun findHikingTrail(input: List<String>, zero: Pair<Int, Int>): Int {
    fun dfs(curr: Pair<Int, Int>, seen: Set<Pair<Int, Int>>): Set<Pair<Int, Int>>? {
        if (seen.contains(curr)) return null
        if (input[curr.first][curr.second] == '9') return setOf(curr)
        return nsewDirections.mapNotNull {
            val next = curr.plus(it)
            val nextValue = input.get(next)
            val currValue = input.get(curr)!!
            if (!input.inBounds(next) || nextValue == null || nextValue - currValue != 1) return@mapNotNull null
            dfs(next, seen + curr)
        }.flatten().toSet()
    }
    return dfs(zero, emptySet())?.count() ?: 0
}

private fun findHikingTrail2(input: List<String>, zero: Pair<Int, Int>): Int {
    fun dfs(curr: Pair<Int, Int>, seen: Set<Pair<Int, Int>>): Int {
        if (seen.contains(curr)) return 0
        if (input[curr.first][curr.second] == '9') return 1
        return nsewDirections.map { dir ->
            val next = curr.plus(dir)
            val nextValue = input.get(next)
            val currValue = input.get(curr)!!
            if (!input.inBounds(next) || nextValue!! - currValue != 1) return@map 0
            dfs(next, seen + curr)
        }.sum()
    }
    return dfs(zero, emptySet())
}


fun part2(input: List<String>): Int {
    return input.toRowColIndices()
        .filter { input[it.first][it.second] == '0' }
        .sumOf { findHikingTrail2(input, it) }
}

fun main() {
    val input = readInput("year2024/day10/Day10")
    part1(input).println()
    part2(input).println()
}
