package year2024.day13

import println
import readInput


data class Button(val type: String, val x: Int, val y: Int) {
    fun cost(): Int = if (type == "A") 3 else 1
}

fun Triple<Double, Double, Long>.isWhole(): Boolean {
    return first % 1.0 == 0.0 && second % 1.0 == 0.0
}

fun findMin(buttons: List<Button>, target: Pair<Long, Long>): Triple<Double, Double, Long> {
    // Hint
    // this is a simple system of equation
    // let g be the number of times need to press button x
    // let h be the number of times needed to press button y

    // g*a.x + h*bx = targetX
    // g*a.y + h * b.y = targetY

    // solving using matrix
    // X = A^1 * B

    // reference https://www.mathcentre.ac.uk/resources/uploaded/sigma-matrices8-2009-1.pdf

    val (a, b) = buttons
    val determinant = (a.x * b.y) - (a.y * b.x)

    val aTimes = ((b.y * target.first) + (-b.x * target.second)).toDouble().div(determinant)
    val bTimes = ((-a.y * target.first) + (a.x * target.second)).toDouble().div(determinant)

    val cost = (aTimes * a.cost()) + (bTimes * b.cost())
    return Triple(aTimes, bTimes, cost.toLong())
}

fun part1(input: List<Pair<List<Button>, Pair<Long, Long>>>): Long {
    return input.map { (buttons, target) -> findMin(buttons, target) }
        .filter { it.isWhole() }.sumOf { it.third }
}

fun part2(input: List<Pair<List<Button>, Pair<Long, Long>>>): Long {
    val delta = 10000000000000
    return input.map { (buttons, target) ->
        findMin(
            buttons,
            target.copy(target.first + delta, target.second + delta)
        )
    }
        .filter { it.isWhole() }.sumOf { it.third }
}

fun process(input: List<String>): Pair<List<Button>, Pair<Long, Long>> {
    val patternButton = "Button\\s([AB]):\\s+X\\+(\\d+),\\s+Y\\+(\\d+)".toRegex()
    val prizePattern = "Prize: X=(\\d+), Y=(\\d+)".toRegex()

    val (buttonA, buttonB, prize) = input
    val (typeA, x1, y1) = patternButton.find(buttonA)!!.destructured
    val (typeB, x2, y2) = patternButton.find(buttonB)!!.destructured
    val (prizeX, prizeY) = prizePattern.find(prize)!!.destructured

    return Pair(
        listOf(Button(typeA, x1.toInt(), y1.toInt()), Button(typeB, x2.toInt(), y2.toInt())),
        prizeX.toLong() to prizeY.toLong()
    )
}

fun main() {
    val input =
        readInput("year2024/day13/Day13").filterNot { it.isBlank() }.chunked(3).map(::process)
    part1(input).println()
    part2(input).println()
}
