package year2024.day09

import println
import readInput


data class Block(val id: Int, val blocks: Int, val freeSpace: Int)

private fun String.toBlocks(): List<Block> {
    return this.toCharArray().toList().chunked(2).mapIndexed { id, layout ->
        Block(id, layout.first().digitToInt(), layout.drop(1).firstOrNull()?.digitToInt() ?: 0)
    }
}

private fun String.toDisk(): MutableList<Int?> {
    return this.toBlocks().fold(mutableListOf()) { acc, devise ->
        (0 until devise.blocks).forEach { _ -> acc.add(devise.id) }
        (0 until devise.freeSpace).forEach { _ -> acc.add(null) }
        acc
    }
}


fun part1(input: String): Long {
    val layout: MutableList<Int?> = input.toDisk()
    var end = layout.size - 1
    var start = 0
    while (start != end && start in layout.indices && end in layout.indices) {
        while (end in layout.indices && layout[end] == null) layout.removeAt(end--)
        while (start in layout.indices && layout[start] != null) start++
        if (start in layout.indices) layout[start++] = layout.removeAt(end--)
    }
    return layout.mapIndexedNotNull { idx, ch -> ch?.times(idx.toLong()) }.sum()
}


fun part2(input: String): Int {
    return 0
}

fun main() {
    val input = readInput("year2024/day09/Day09")
    part1(input.first()).println()
    part2(input.first()).println()
}
