package year2024.day06

import println
import readInput

class Direction() {
    private var current = 0
    private val directions = listOf(Pair(-1, 0), Pair(0, 1), Pair(1, 0), Pair(0, -1))
    fun change() {
        current = (current + 1) % 4
    }

    fun next(curr: Pair<Int, Int>): Pair<Int, Int> {
        val (dx, dy) = current()
        return Pair(dx + curr.first, dy + curr.second)
    }

    private fun current(): Pair<Int, Int> {
        return directions[current]
    }


}

fun startPosition(input: List<String>): Pair<Int, Int> {
    return input.flatMapIndexed { r, row ->
        row.mapIndexed { c, char -> Triple(r, c, char) }
    }.firstOrNull { it.third == '^' }?.let { Pair(it.first, it.second) }!!
}

fun part1(input: List<String>): Int {
    val start = startPosition(input)
    val visited = mutableSetOf<Pair<Int, Int>>()
    var row = start.first
    var col = start.second
    visited.add(start)
    val direction = Direction()
    while (row in input.indices && col in input[0].indices) {
        var nextStep = direction.next(Pair(row, col))
        if (input.getOrNull(nextStep.first)?.getOrNull(nextStep.second) == '#') {
            direction.change()
            nextStep = direction.next(Pair(row, col))
        }
        row = nextStep.first
        col = nextStep.second
        if (row in input.indices && col in input[0].indices) {
            visited.add(row to col)
        }
    }
    return visited.count()
}

fun part2(input: List<String>): Int {
    return input.size
}

fun main() {
    val input = readInput("year2024/day06/Day06")
    part1(input).println()
    part2(input)
}

// 0, 1
// 1,0
// 0,-1
// -1,0
