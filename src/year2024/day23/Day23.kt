package year2024.day23

import println
import readInput

fun part1(input: List<String>): Int {
    val graph = input.fold(emptyMap<String, Set<String>>()) { acc, s ->
        val (a, b) = s.split("-")
        val aChildren = (acc[a] ?: emptySet()) + b
        val bChildren = (acc[b] ?: emptySet()) + a
        acc + (a to aChildren) + (b to bChildren)
    }

    val connectedComponents = mutableSetOf<Set<String>>()


    fun dfs(island: Set<String>, start: String, computer: String, level: Int = 0) {
        if (level > 3) return

        graph[computer]?.forEach {
            if (it == start && level == 2) {
                connectedComponents.add(island)
            }
            if (it in island) return@forEach
            dfs(island + it, it, start, level + 1)
        }
    }

    graph.keys.forEach {
        dfs(setOf(it), it, it)
    }
    return connectedComponents.count { it.any { c -> c.startsWith("t") } }
}


fun part2(input: List<String>): Long {
    val graph = input.fold(emptyMap<String, Set<String>>()) { acc, s ->
        val (a, b) = s.split("-")
        val aChildren = (acc[a] ?: emptySet()) + b
        val bChildren = (acc[b] ?: emptySet()) + a
        acc + (a to aChildren) + (b to bChildren)
    }

    val allVertices = graph.keys

    val cliques = mutableSetOf<Set<String>>()

    fun bk(r: Set<String>, p: Set<String>, x: Set<String>) {
        if (p.isEmpty() && x.isEmpty()) {
            cliques.add(r)
            return
        }

        p.forEach {
            bk(
                r + it,
                graph[it]!!.intersect(p + it).toMutableSet(),
                graph[it]!!.intersect(x - it).toMutableSet()
            )
        }
    }

    bk(emptySet(), allVertices.toMutableSet(), mutableSetOf())

    cliques.maxBy {  it.count()}.toList().sorted().joinToString(",").println()

    return 0L
}

fun main() {
    val input = readInput("year2024/day23/sample")
//    part1(input).println()
    part2(input).println()
}


// c -> b
// c -> a
// a -> b

//
