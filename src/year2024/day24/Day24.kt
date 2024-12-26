package year2024.day24

import println
import readInput

val logicFunctions: Map<String, (String, String) -> String> = mapOf(
    "XOR" to { a, b -> if (a != b) "1" else "0" },
    "OR" to { a, b -> if (a == "1" || b == "1") "1" else "0" },
    "AND" to { a, b -> if (a == "1" && b == "1") "1" else "0" }
)




fun part1(
    resolvedGates: MutableMap<String, String>,
    connections: Map<String, Triple<String, String, String>>
): Long {
    fun resolve(gate:String): String {
        resolvedGates[gate]?.let { return it }
        val(left,op,right) = connections[gate]!!
        val leftVal = resolve(left)
        val rightVal = resolve(right)
        val result = logicFunctions[op]!!(leftVal,rightVal)
        resolvedGates[gate] = result
        return result
    }

    return connections.keys.filter { it.startsWith("z") }.map {
        it to resolve(it)
    }.sortedByDescending { it.first }.joinToString("") { it.second }.toLong(2)
}

fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val input = readInput("year2024/day24/Day24")
    val resolvedGates = input.takeWhile { it.isNotBlank() }.associate {
        val (a, b) = it.split(":\\s+".toRegex())
        a to b
    }
    val pattern = "([a-z0-9]+)\\s+(XOR|OR|AND)\\s+([a-z0-9]+)".toRegex()
    val connections = input
        .dropWhile { it.isNotBlank() }
        .drop(1).associate {
            val (exp, out) = it.split("\\s+->\\s+".toRegex())
            val (left, op, right) = pattern.find(exp)!!.destructured
            out to Triple(left.trim(), op.trim(), right.trim())
        }

    part1(resolvedGates.toMutableMap(),connections).println()
    part2(input).println()
}
