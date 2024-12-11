package year2024.day03

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val pattern = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")
        return input
            .flatMap { pattern.findAll(it).map { r -> r.groupValues.drop(1) } }
            .sumOf { (a,b) -> a.toInt() * b.toInt() }
    }

    fun part2(input: List<String>): Int {
        val mulPattern = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")
        val pattern = Regex("mul\\(\\d{1,3},\\d{1,3}\\)|don't\\(\\)|do\\(\\)")

        val findAllMatches: (String) -> Sequence<String> = { pattern.findAll(it).map(MatchResult::value)}
        val evaluateMul: (String) -> Int = { mulPattern.find(it)!!.groupValues.drop(1).map(String::toInt).reduce { i, acc -> acc * i } }

        var takeIt = true
        val enabled: (String) -> Boolean = { it ->
            when (it) {
                "do()" -> takeIt = true
                "don't()" -> takeIt = false
            }
            takeIt && mulPattern.matches(it)
        }

        return input.flatMap(findAllMatches).filter(enabled).sumOf(evaluateMul)
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("year2024/day03/Day03")
    part1(input).println()
    part2(input).println()
}
