package year2024.day22

import println
import readInput

fun mix(a: Long, b: Long): Long {
    return a xor b
}

fun prune(a: Long): Long {
    return a % 16777216
}

fun nextSequence(secretNumber: Long): Long {
    val s1 = prune(mix(secretNumber * 64, secretNumber))
    val s2 = prune(mix(s1.div(32), s1))
    return prune(mix(s2 * 2048, s2))
}

fun first2kth(a: Long, count: Int = 2000): List<Long> {
    return (1..count).fold(listOf(a)) { acc, _ ->
        acc + nextSequence(acc.last())
    }
}

fun toDiff(b: List<Long>): Map<String, Long> {
    val ones = b.map { it % 10 }
    val onesToDiff = ones.mapIndexed { i, v ->
        v to ones.getOrNull(i - 1)?.let { v - it }
    }.drop(1)
    val mapNum = mutableMapOf<String, Long>()
    onesToDiff.forEachIndexed { i, _ ->
        if (i + 3 >= onesToDiff.size) return@forEachIndexed
        val key =
            "${onesToDiff[i].second},${onesToDiff[i + 1].second},${onesToDiff[i + 2].second},${onesToDiff[i + 3].second}"
        mapNum.putIfAbsent(key, onesToDiff[i + 3].first)
    }
    return mapNum
}

fun part1(input: List<Long>): Long {
    return input.sumOf { first2kth(it).last() }
}


fun part2(input: List<Long>): Long {
    val c = input.map { toDiff(first2kth(it)) }

    val uniqueSequences = c.map { it.keys }.fold(emptySet<String>()) { acc, ks -> acc + ks }

    return uniqueSequences.map { seq ->
        seq to c.sumOf { it[seq] ?: 0 }
    }.maxBy { it.second }.second
}

fun main() {
    val input = readInput("year2024/day22/Day22").map { it.toLong() }
    part1(input).println()
    part2(input).println()
}
