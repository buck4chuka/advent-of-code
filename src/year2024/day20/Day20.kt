package year2024.day20

import Direction
import println
import readInput
import year2024.day16.plus
import kotlin.math.abs


private fun Pair<Int, Int>.manhattanDistance(neighbour: Pair<Int, Int>): Int {
    return abs(first - neighbour.first) + abs(second - neighbour.second)
}

private fun List<String>.toCoordinates(): List<Pair<Int, Int>> {
    return this.indices.flatMap { r -> this.first().indices.map { c -> r to c } }
}

private fun List<String>.findPosition(char: Char): Pair<Int, Int> {
    return this.toCoordinates().first { this[it.first][it.second] == char }
}

private fun List<String>.findAllPositions(char: Char): Set<Pair<Int, Int>> {
    return this.toCoordinates().filter { this[it.first][it.second] == char }.toSet()
}

private fun Pair<Int, Int>.within(radius: Int): List<Pair<Int, Int>> {
    return ((first - radius)..(first + radius)).flatMap { r ->
        ((second - radius)..(second + radius)).map { c ->
            r to c
        }
    }
}

private fun findPath(input: List<String>): List<Pair<Int, Int>> {
    val start = input.findPosition('S')
    val end = input.findPosition('E')
    val walls = input.findAllPositions('#')
    val path = mutableListOf(start)

    val isValid: (Pair<Int, Int>) -> Boolean = {
        it !in walls && (it.first in input.indices && it.second in input.first().indices)
    }


    while (path.last() != end) {
        Direction.entries.map { path.last() + it }
            .firstOrNull { isValid(it) && it != path.dropLast(1).lastOrNull() }
            ?.let { path.add(it) }
    }

    return path.toList()
}

private fun findCheatCodeThatSavesHundred(input: List<String>, cheatSeconds: Int): Int {
    val walls = input.findAllPositions('#')
    val path = findPath(input)

    val isValid: (Pair<Int, Int>) -> Boolean = {
        it !in walls && (it.first in input.indices && it.second in input.first().indices)
    }

    val totalPathTime = path.size - 1
    val timeToEnd = path.mapIndexed { currTime, coord -> coord to totalPathTime - currTime }.toMap()


    val cheatSavedTimes = path.flatMapIndexed { time, point ->
        point.within(cheatSeconds)
            .map { it to it.manhattanDistance(point) }
            .filter { it.second <= cheatSeconds }
            .filter { isValid(it.first) }
            .map { (p1, dist) -> totalPathTime - (time + dist + timeToEnd[p1]!!) }
    }

    return cheatSavedTimes.count { it >= 100 }
}

fun part1(input: List<String>): Int {
    return findCheatCodeThatSavesHundred(input, 2)
}

fun part2(input: List<String>): Int {
    return findCheatCodeThatSavesHundred(input, 20)
}

fun main() {
    val input = readInput("year2024/day20/Day20")
    part1(input).println()
    part2(input).println()
}
