package year2024.day11

import println
import readInput

data class Stone(val engraving: String) {
    private val halfLen = engraving.length / 2
    fun blink(): List<Stone> = when {
        engraving == "0" -> listOf(Stone("1"))
        engraving.length % 2 == 0 -> listOf(
            Stone(engraving.substring(0, halfLen)),
            Stone(engraving.substring(halfLen).trimStart('0').ifEmpty { "0" })
        )
        else -> listOf(Stone((engraving.toLong() * 2024).toString()))
    }
}

fun List<Stone>.blink(blinkTimes: Int): Long {
    val memo = mutableMapOf<Pair<Stone, Int>, Long>()
    fun calculate(stone: Stone, times: Int): Long = memo.getOrPut(stone to times) {
        if (times == 1) return@getOrPut stone.blink().size.toLong()
        stone.blink().sumOf { calculate(it, times - 1) }
    }
    return this.sumOf { calculate(it, blinkTimes) }
}

fun part1(stones: List<Stone>): Long = stones.blink(25)
fun part2(stones: List<Stone>): Long = stones.blink(75)

fun main() {
    val input = readInput("year2024/day11/sample")
    val stones = input.first().split("\\s+".toRegex()).map { Stone(it.trim()) }
    part1(stones).println()
    part2(stones).println()
}
