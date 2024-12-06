package year2024.day05

import println
import readInput

fun parseEdges(input: List<String>): List<Pair<Int, Int>> {
    val pattern = "(\\d+)\\|(\\d+)".toRegex()
    return input
        .mapNotNull { pattern.find(it)?.destructured?.let { (first, second) -> first.toInt() to second.toInt() } }
}

fun parseOrders(input: List<String>): List<Map<Int, Int>> {
    return input
        .filter { it.contains(",") }
        .map { it.split(",").mapIndexed { index, num -> num.toInt() to index }.toMap()
    }
}

fun isTopSorted(edges: List<Pair<Int, Int>>, order: Map<Int, Int>): Boolean {
    return edges.filter {(u,v) -> u in order && v in order }.all { (u, v) -> order[u]!! < order[v]!! }
}

fun topSort(graph: Map<Int, Set<Int>>, pages: Set<Int>): List<Int> {
    val filteredGraph = graph.filterKeys { it in pages }.mapValues { it.value.intersect(pages) }
    val ordered = mutableListOf<Int>()
    val visited = mutableSetOf<Int>()

    fun dfs(node: Int) {
        if (visited.contains(node)) return
        filteredGraph[node]?.forEach(::dfs)
        visited.add(node)
        ordered.add(node)
    }
    pages.forEach(::dfs)
    return ordered
}

fun part1(input: List<String>): Int {
    return parseOrders(input)
        .filter { isTopSorted(parseEdges(input), it) }
        .map { it.entries.associate { (k, v) -> v to k } }
        .sumOf { it[it.size / 2]!! }
}

fun part2(input: List<String>): Int {
    val graph =
        parseEdges(input).groupBy { it.first }.mapValues { (_, v) -> v.map { it.second }.toSet() }
    return parseOrders(input)
        .filter { !isTopSorted(parseEdges(input), it) }
        .map { topSort(graph, it.keys) }.sumOf { it[it.size / 2] }
}

fun main() {
    val input = readInput("year2024/day05/Day05")
    part1(input).println()
    part2(input).println()
}
