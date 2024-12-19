package year2024.day16

import Direction
import println
import readInput
import java.util.PriorityQueue

val validLocations = setOf('E', 'S', '.')


private data class Node(
    val coordinate: Pair<Int, Int>,
    val direction: Direction,
    val weight: Int,
    val parents: List<Pair<Int, Int>> = emptyList()
)

private fun List<String>.isValidLocation(coordinate: Pair<Int, Int>): Boolean {
    return this.getOrNull(coordinate.first)?.getOrNull(coordinate.second) in validLocations
}

 operator fun Pair<Int, Int>.plus(other: Direction): Pair<Int, Int> {
    return first + other.r to second + other.c
}

private fun computeDistance(
    start: Pair<Int, Int>,
    input: List<String>,
    direction: Direction = Direction.EAST
): Map<Pair<Int, Int>, Node> {
    val pq = PriorityQueue<Node>(compareBy { it.weight })

    val visited = mutableSetOf<Pair<Int, Int>>()

    val shortestPath = mutableMapOf<Pair<Int, Int>, Node>()

    pq.add(Node(coordinate = start, direction = direction, weight = 0))
    shortestPath[start] = Node(coordinate = start, direction = Direction.EAST, weight = 0)

    while (!pq.isEmpty()) {
        val current = pq.poll()
        visited.add(current.coordinate)

        Direction.entries.forEach { dir ->

            val penaltyForTurning = if (dir != current.direction) 1000 else 0

            val neighbour = current.coordinate + dir
            if (neighbour in visited) return@forEach
            if (input.isValidLocation(neighbour)) {
                val currentWeight = shortestPath[current.coordinate]!!.weight
                val nextNode = Node(neighbour, dir, currentWeight + penaltyForTurning + 1)
                val previousNextNode =
                    shortestPath.getOrDefault(neighbour, Node(neighbour, dir, Int.MAX_VALUE))
                val shortest = listOf(previousNextNode, nextNode).minBy { it.weight }
                shortestPath[neighbour] = shortest
                if (neighbour !in visited) pq.add(nextNode)
            }

        }
    }
    return shortestPath
}

fun part1(input: List<String>, start: Pair<Int, Int>, end: Pair<Int, Int>): Int {
    val shortestPath = computeDistance(start, input)
    return shortestPath[end]!!.weight
}


fun part2(input: List<String>, start: Pair<Int, Int>, end: Pair<Int, Int>): Int {
    val distancesFromStart = computeDistance(start, input)
    val bestScore = distancesFromStart[end]!!.weight


   return input.indices.flatMap { r -> input.first().indices.map { c -> r to c } }
        .filter { coord ->
            val startToCurr = distancesFromStart[coord] ?: return@filter false
            val currToEnd = computeDistance(coord,input,startToCurr.direction)[end] ?: return@filter false
            startToCurr.weight + currToEnd.weight == bestScore
        }.toSet().size
}



fun main() {
    val input = readInput("year2024/day16/Day16")
        .map { it.drop(1).dropLast(1) }
        .dropLast(1)
        .drop(1)
    val start = input.indices
        .flatMap { r -> input.first().indices.map { c -> r to c } }
        .first { (r, c) -> input[r][c] == 'S' }
    val end = input.indices
        .flatMap { r -> input.first().indices.map { c -> r to c } }
        .first { (r, c) -> input[r][c] == 'E' }


    part1(input, start, end).println()
    part2(input, start, end).println()
}

