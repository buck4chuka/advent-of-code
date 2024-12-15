package year2024.day14

import println
import readInput
import kotlin.math.abs

private fun Pair<Int, Int>.moveRobot(
    velocity: Pair<Int, Int>,
    maxPosition: Pair<Int, Int>
): Pair<Int, Int> {
    return (maxPosition.first + first + velocity.first) % maxPosition.first to (maxPosition.second + second + velocity.second) % maxPosition.second
}

private fun Pair<Int, Int>.moveRobot2(
    velocity: Pair<Int, Int>,
    maxPosition: Pair<Int, Int>
): Pair<Pair<Int, Int>, Pair<Int, Int>> {
    val newPos =
        (maxPosition.first + first + velocity.first) % maxPosition.first to (maxPosition.second + second + velocity.second) % maxPosition.second
    return newPos to velocity
}

fun simulatePosition(
    cycles: Int,
    positionVelocity: Pair<Pair<Int, Int>, Pair<Int, Int>>,
    maxPosition: Pair<Int, Int>
): Pair<Int, Int> {
    val (position, velocity) = positionVelocity
    val seen = mutableMapOf<Pair<Int, Int>, Int>()
    var currPosition = position
    // to sim = 13 - 5 % 3


// 3 2 4 3 2 4 3 2 4 3 2 4 3 2
// 0 1 2 3 4 5 6 7 8 9 0 1 2 3

    // 14 - 3 + 1 == 10 % 3 == 1

    var repeatedAt: Int? = null

    for (i in 0 until cycles) {
        if (currPosition in seen) {
            repeatedAt = i
            break
        }
        seen[currPosition] = i
        currPosition = currPosition.moveRobot(velocity, maxPosition)
    }

    if (repeatedAt == null) return currPosition

    val loopLength = repeatedAt * seen[currPosition]!!
    val remainingToSimulate = cycles - repeatedAt + 1

    repeat(remainingToSimulate % loopLength) {
        currPosition = currPosition.moveRobot(velocity, maxPosition)
    }

    return currPosition
}

fun dangerScore(robots: List<Pair<Int, Int>>): Int {
    val quadrants = maxRangeToQuadrants(101 to 103)
    return robots
        .groupBy { item ->
            quadrants.find { quadrant -> item.first in quadrant.first && item.second in quadrant.second }
        }.filterKeys { it != null }.mapValues { (_, v) -> v.size }
        .values.fold(1) { acc: Int, i: Int -> acc * i }
}

fun part1(input: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>): Int {
    val robots = input.map { simulatePosition(100, it, 101 to 103) }
    return dangerScore(robots)
}

fun part2(input: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>): Int {
    var robots = input
    val maxPosition = 101 to 103
    var count = 0
    repeat(100_000) {
        robots =
            robots.map { (position, velocity) -> position.moveRobot2(velocity, maxPosition) }
        count++
        maybeTree(maxPosition, robots)?.let { output ->
            "--------\n".println()
            output.forEach {
                println(it.joinToString("")) }
            "--------\n".println()
            return count
        }
    }
    return -1
}

private fun maybeTree(
    maxPosition: Pair<Int, Int>,
    robots: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>
): List<List<Char>>? {
    // Create a set of robot positions for quick lookup
    val robotSet = robots.map { it.first }.toSet()

    // Generate the grid with robots marked as '*'
    val robotGrid = List(maxPosition.second) { row ->
        List(maxPosition.first) { col ->
            if (col to row in robotSet) '*' else ' '
        }
    }

    // Check for a possible tree with at least 15 consecutive positions
    val hasTree = robotGrid.any { row ->
        row.mapIndexed { i, char -> i to char }
            .filter { it.second == '*' }
            .zipWithNext()
            .count { (a, b) -> abs(a.first - b.first) == 1 } >= 15
    }

    return if (hasTree) robotGrid else null
}

fun MutableList<MutableList<Char>>.string(): String {
    return this.joinToString("\n") { it.toList().joinToString("") }
}

fun maxRangeToQuadrants(maxPosition: Pair<Int, Int>): List<Pair<IntRange, IntRange>> {
    val (x, y) = maxPosition
    val firstX = 0 until x / 2
    val secondX = x / 2 + 1 until x

    val firstY = 0 until (y / 2)
    val secondY = y / 2 + 1 until y

    val quadrant1 = Pair(firstX, firstY)
    val quadrant2 = Pair(firstX, secondY)
    val quadrant3 = Pair(secondX, firstY)
    val quadrant4 = Pair(secondX, secondY)

    return listOf(quadrant1, quadrant2, quadrant3, quadrant4)
}

fun parseInput(line: String): Pair<Pair<Int, Int>, Pair<Int, Int>> {
    val pattern = "p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)".toRegex()

    val (px, py, vx, vy) = pattern.find(line)!!.destructured
    return Pair(px.toInt() to py.toInt(), vx.toInt() to vy.toInt())
}

fun main() {
    val input = readInput("year2024/day14/Day14").map(::parseInput)
    part1(input).println()
    part2(input).println()
}

