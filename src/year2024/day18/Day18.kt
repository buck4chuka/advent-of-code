package year2024.day18

import Direction
import println
import readInput

import year2024.day16.plus


private class Vertex(val coords: Pair<Int, Int>, val steps:Int, val prev: Vertex? = null) {}
fun part1(input: List<Pair<Int, Int>>,end:Pair<Int,Int> = 70 to 70, firstBytes:Int = 1024): Int? {
    val unsafe = input.take(firstBytes).toSet()
    val distances = shortestPath(unsafe,end)

    return distances[end]?.steps
}

private fun shortestPath(unsafe: Set<Pair<Int, Int>>,end: Pair<Int, Int> = 70 to 70): Map<Pair<Int, Int>, Vertex> {
    val c = ArrayDeque<Vertex>()
    c.add(Vertex(0 to 0, 0))

    val seen = mutableMapOf<Pair<Int, Int>, Vertex>()

    val isInbounds: (coords: Pair<Int, Int>) -> Boolean = {
        it.first in 0..end.first && it.second in 0.. end.second
    }

    while (c.isNotEmpty()) {
        val curr = c.removeFirst()
        if (curr.coords in seen) continue
        seen[curr.coords] = curr

        if (curr.coords == end) break

        Direction.entries.map { curr.coords + it }
            .filter { !seen.contains(it) && isInbounds(it) && it !in unsafe }
            .forEach {
                c.add(Vertex(it, curr.steps + 1, prev = curr))
            }
    }
    return seen
}

fun part2(input: List<Pair<Int, Int>>,end:Pair<Int,Int> = 70 to 70, firstBytes:Int = 1024): Pair<Int,Int>? {
    val unsafe = input.take(firstBytes).toMutableSet()
    input.drop(firstBytes).forEach {
        unsafe.add(it)
        if (shortestPath(unsafe + it, end)[end] == null)  return it
    }
return null
}




fun main() {
    val input = readInput("year2024/day18/Day18").map {
        val(col,row) = it.split(',').map(String::toInt)
        row to col
    }
    part1(input).println()
    part2(input).println()
}

