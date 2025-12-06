package year2025.day04

import directions
import println
import readInput


fun findMovableRolls(input: List<List<Char>>): Set<Pair<Int, Int>> {
    return input
        .indices
        .flatMap { r -> input[0].indices.map { c -> r to c } }
        .filter { (r, c) ->
            input.getOrNull(r)?.getOrNull(c) == '@' &&
            directions
                .mapNotNull { (dr, dc) -> input.getOrNull(r + dr)?.getOrNull(c + dc) }
                .count { it == '@' } < 4
        }.toSet()
}

fun calculateTotalMovable(grid: List<List<Char>>, accumulatedCount: Int = 0): Int {
    val movableRolls = findMovableRolls(grid)

    if (movableRolls.isEmpty()) {
        return accumulatedCount
    }

    val nextGrid = grid.mapIndexed { r, row ->
        row.mapIndexed { c, char -> if (movableRolls.contains(Pair(r, c))) '.' else char }
    }

    return calculateTotalMovable(
        nextGrid,
        accumulatedCount + movableRolls.size
    )
}

fun main() {
    fun part1(input: List<String>): Int {
        return findMovableRolls(input.map { it.toList() }).size
    }

    fun part2(input: List<String>): Int {
        return calculateTotalMovable(input.map { it.toList() })
    }

    val testInput = readInput("year2025/day04/test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 43)

    val input = readInput("year2025/day04/input")
    part1(input).println()
    part2(input).println()
}
