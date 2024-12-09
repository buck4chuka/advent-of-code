//package year2024.day10
//
//import println
//import readInput
//
//
//fun part1(input: String): Long {
//    val layout: MutableList<Int?> = input.toLayout1()
//    var end = layout.size - 1
//    var start = 0
//    while (start != end && start in layout.indices && end in layout.indices) {
//        while (end in layout.indices && layout[end] == null) layout.removeAt(end--)
//        while (start in layout.indices && layout[start] != null) start++
//        if (start in layout.indices) layout[start++] = layout.removeAt(end--)
//    }
//    return layout.mapIndexedNotNull { idx, ch -> ch?.times(idx.toLong()) }.sum()
//}
//
//
//fun freeSpaceStartToSize(layout: MutableList<Int?>): List<Pair<Int,Int>> {
//    var start: Int? = null
//    var currSize = 0
//    val result = mutableSetOf<Pair<Int,Int>>()
//
//    layout.forEachIndexed { idx, id ->
//       if (id == null) {
//           if(start == null) start = idx
//           currSize ++
//           return@forEachIndexed
//       }
//        if(start != null){
//            result.add(start!! to currSize)
//            start = null
//            currSize = 0
//        }
//    }
//    if(start != null){result.add(start!! to currSize)}
//    return result.toList().sortedBy { it.first }
//}
//
//
//fun part2(input: String): Int {
//    return 0
//}
//
//fun main() {
//    val input = readInput("year2024/day09/Day09")
//    part1(input.first()).println()
//    part2(input.first()).println()
//}
