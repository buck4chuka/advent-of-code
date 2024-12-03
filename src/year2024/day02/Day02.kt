package year2024.day02

import println
import readInput
import kotlin.math.absoluteValue

fun main() {
    fun isValid(report: List<Int>): Boolean {
        val distance = report.zipWithNext().map { (a, b) -> a - b }
        return (distance.all { it > 0 } || distance.all { it < 0 }) && distance.all { it.absoluteValue in 1..3 }
    }

    fun removeOneValid(report: List<Int>) : Boolean {
        return isValid(report) || report.indices.map { isValid(report.subList(0, it) + report.subList(it + 1, report.size)) }.any { it }
    }

    fun part1(reports: List<List<Int>>): Int {
        return reports.count { isValid(it) }
    }

    fun part2(reports: List<List<Int>>): Int {
        return reports.count { removeOneValid(it) }
    }

    val input = readInput("year2024/day02/Day02").map { line -> line.split("\\s+".toRegex()).map { it.toInt() } }
    part1(input).println()
    part2(input).println()
}
