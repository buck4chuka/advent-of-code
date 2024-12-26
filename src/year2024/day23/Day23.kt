package year2024.day23

import println
import readInput

fun part1(graph: Map<String, Set<String>>): Int {
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

fun findCliques(graph: Map<String, Set<String>>): Set<Set<String>> {
    val allVertices = graph.keys

    val cliques = mutableSetOf<Set<String>>()

    fun bronKerbosch(r: Set<String>, p: Set<String>, x: Set<String>) {
        if (p.isEmpty() && x.isEmpty()) {
            cliques.add(r)
            return
        }

        val v = (p.union(x)).random()


        (p - graph[v]!!).forEach {
            bronKerbosch(
                r + it,
                graph[it]!!.intersect(p + it).toMutableSet(),
                graph[it]!!.intersect(x - it).toMutableSet()
            )
        }
    }

    bronKerbosch(emptySet(), allVertices.toMutableSet(), mutableSetOf())

    return cliques.toSet()
}


fun part2(graph: Map<String, Set<String>>): String {
    return findCliques(graph).maxBy { it.count() }.toList().sorted().joinToString(",")
}

fun main() {
    val input = readInput("year2024/day23/Day23")
    val graph = input.fold(emptyMap<String, Set<String>>()) { acc, s ->
        val (a, b) = s.split("-")
        val aChildren = (acc[a] ?: emptySet()) + b
        val bChildren = (acc[b] ?: emptySet()) + a
        acc + (a to aChildren) + (b to bChildren)
    }
    part1(graph).println()
    part2(graph).println()
}
