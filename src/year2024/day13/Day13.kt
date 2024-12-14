package year2024.day13

import println
import readInput

fun Triple<Double, Double, Double>.isWhole() = first % 1.0 == 0.0 && second % 1.0 == 0.0

fun Pair<Long,Long>.add(delta:Long) = this.copy(first = first + delta, second = second + delta)

fun findMin(input: Triple<Pair<Long, Long>, Pair<Long, Long>, Pair<Long, Long>>): Triple<Double, Double, Double> {
    val (a, b, target) = input
    // Hint: this is a simple system of equation
    // let g be the number of times need to press button x
    // let h be the number of times needed to press button y
    // g * a.x + h * b.x = target.x
    // g * a.y + h * b.y = target.y
    // solving using matrix
    // X = A^1 * B
    // reference https://www.mathcentre.ac.uk/resources/uploaded/sigma-matrices8-2009-1.pdf
    // solution must be a whole number to be valid

    val determinant = (a.first * b.second) - (a.second * b.first)
    val aTimes =
        (b.second * target.first + -b.first * target.second).toDouble().div(determinant)
    val bTimes =
        (-a.second * target.first + a.first * target.second).toDouble().div(determinant)
    return Triple(aTimes, bTimes, aTimes * 3 + bTimes * 1)
}

fun part1(input: List<Triple<Pair<Long, Long>, Pair<Long, Long>, Pair<Long, Long>>>): Long {
    return input.map(::findMin).filter { it.isWhole() }.sumOf { it.third.toLong() }
}

fun part2(input: List<Triple<Pair<Long, Long>, Pair<Long, Long>, Pair<Long, Long>>>): Long {
    val delta = 10000000000000
    return input
        .map { (a, b, target) -> findMin(Triple(a, b, target.add(delta)))
        }.filter { it.isWhole() }.sumOf { it.third.toLong() }
}

fun process(input: List<String>): Triple<Pair<Long, Long>, Pair<Long, Long>, Pair<Long, Long>> {
    val patternButton = "Button [AB]: X\\+(\\d+), Y\\+(\\d+)".toRegex()
    val prizePattern = "Prize: X=(\\d+), Y=(\\d+)".toRegex()

    val (buttonA, buttonB, prize) = input
    val (x1, y1) = patternButton.find(buttonA)!!.destructured
    val (x2, y2) = patternButton.find(buttonB)!!.destructured
    val (prizeX, prizeY) = prizePattern.find(prize)!!.destructured

    return Triple(
        Pair(x1.toLong(), y1.toLong()),
        Pair(x2.toLong(), y2.toLong()),
        prizeX.toLong() to prizeY.toLong()
    )
}

fun main() {
    val input = readInput("year2024/day13/Day13").filterNot { it.isBlank() }.chunked(3).map(::process)
    part1(input).println()
    part2(input).println()
}
