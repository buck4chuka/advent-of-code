package year2024.day08

import Point2D
import println
import readInput

data class Line(val a: Point2D, val b: Point2D) {
    fun equidistantPointsFromEnds(): Set<Point2D> = setOf(a + a - b, b + b - a)
    private fun generatePointsInDirection(
        a: Point2D,
        delta: Point2D,
        step: Int
    ): Sequence<Point2D> = generateSequence(0) { it + step }.map {
        Point2D(x = a.x + it * delta.x, y = a.y + it * delta.y)
    }

    fun gridPointsOnLine(input: List<String>): List<Point2D> {
        val isInBounds: (Point2D) -> Boolean = { it.x in input[0].indices && it.y in input.indices }
        return listOf(1, -1)
            .flatMap { dir ->
                generatePointsInDirection(a, a - b, dir).takeWhile(isInBounds)
            }
    }
}

private fun uniquePairsOfAntennaPos(input: List<String>): Sequence<Line> {
    fun List<String>.get(pos: Point2D): Char {
        return this[pos.y][pos.x]
    }

    fun List<String>.toPoint2Ds(): List<Point2D> {
        return this.indices.flatMap { y -> this.first().indices.map { x -> Point2D(x = x, y = y) } }
    }
    return input.toPoint2Ds()
        .filterNot { input.get(it) == '.' }
        .groupBy { input.get(it) }
        .values
        .asSequence()
        .flatMap(::getUniquePairs)
}

fun part1(input: List<String>): Int {
    val isInBounds: (Point2D) -> Boolean =
        { (col, row) -> col in input.indices && row in input.first().indices }
    return uniquePairsOfAntennaPos(input)
        .flatMap { it.equidistantPointsFromEnds() }
        .filter(isInBounds)
        .toSet()
        .count()
}

fun getUniquePairs(list: List<Point2D>): List<Line> {
    return list.flatMapIndexed { index, a ->
        list.subList(index + 1, list.size).map { b -> Line(a, b) }
    }
}

fun part2(input: List<String>): Int {
    return uniquePairsOfAntennaPos(input).flatMap { it.gridPointsOnLine(input) }.toSet().count()
}

fun main() {
    val input = readInput("year2024/day08/Day08")
    part1(input).println()
    part2(input).println()
}
