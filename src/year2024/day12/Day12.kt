package year2024.day12

import nsewDirections
import println
import readInput


fun Pair<Int,Int>.neighbours(): List<Pair<Int,Int>> {
   return nsewDirections.map { it.copy(it.first + this.first, it.second + this.second) }
}
fun List<String>.get(p:Pair<Int,Int>): Char? {
    return this.getOrNull(p.first)?.getOrNull(p.second)
}

fun List<String>.calcAreaPeri(start:Pair<Int,Int>, visited:MutableSet<Pair<Int,Int>>): Long {
    if (visited.contains(start)) return 0
    val grid = this
    var area = 0
    var perimeter = 0
    fun dfs(curr: Pair<Int,Int>) {
        val(row, col) = curr
        val neighbours = curr.neighbours()
        val type = grid[row][col]
        visited.add(curr)
        area ++
        for (neighbour in neighbours) {
            if(type != grid.get(neighbour)) {
                perimeter++
                continue
            }

            if (visited.contains(neighbour)) {
                continue
            }

            grid.get(neighbour)?.let { dfs(neighbour)}
        }

    }
    dfs(start)
    return area.toLong() * perimeter
}

fun part1(input: List<String>): Long {
    val visited = mutableSetOf<Pair<Int,Int>>()
  return  input.indices
        .flatMap { r -> input.indices.map { c -> input.calcAreaPeri(r to c, visited) } }
        .sum()
}



fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val input = readInput("year2024/day12/Day12")
    part1(input).println()
    part2(input).println()
}
