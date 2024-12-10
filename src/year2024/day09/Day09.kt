package year2024.day09

import println
import readInput

data class Block(val id: Int, val blocks: Int, var freeSpace: Int) {
    fun size() = blocks + freeSpace
}

private fun String.toFileBlocks(): List<Block> {
    return this.chunked(2).mapIndexed { id, layout ->
        Block(id, layout[0].digitToInt(), layout.getOrNull(1)?.digitToInt() ?: 0)
    }
}

private fun List<Block>.toDisk(): List<Int?> {
    return this.fold(mutableListOf()) { acc, devise ->
        (0 until devise.blocks).forEach { _ -> acc.add(devise.id) }
        (0 until devise.freeSpace).forEach { _ -> acc.add(null) }
        acc
    }
}

private fun List<Int?>.toCheckSum(): Long =
    this.mapIndexedNotNull { idx, ch -> ch?.times(idx.toLong()) }.sum()

fun part1(input: String): Long {
    val layout = input.toFileBlocks().toDisk().toMutableList()
    var end = layout.size - 1
    var start = 0
    while (start != end && start in layout.indices && end in layout.indices) {
        while (end in layout.indices && layout[end] == null) layout.removeAt(end--)
        while (start in layout.indices && layout[start] != null) start++
        if (start in layout.indices) layout[start++] = layout.removeAt(end--)
    }
    return layout.toCheckSum()
}

fun part2(input: String): Long {
    val fileBlocks = input.toFileBlocks().toMutableList()
    var end = fileBlocks.size - 1
    while (end >= 0) {
        var start = 0
        while (start < end) {
            val current = fileBlocks[start]
            if (current.freeSpace >= fileBlocks[end].blocks) {
                fileBlocks.getOrNull(end - 1)?.let { it.freeSpace += fileBlocks[end].size() }
                fileBlocks[end].freeSpace = current.freeSpace - fileBlocks[end].blocks
                current.freeSpace = 0
                fileBlocks.add(start + 1, fileBlocks.removeAt(end))
                end++
                break
            }
            start += 1
        }
        end--
    }
    return fileBlocks.toDisk().toCheckSum()
}

fun main() {
    val input = readInput("year2024/day09/Day09")
    part1(input.first()).println()
    part2(input.first()).println()
}
