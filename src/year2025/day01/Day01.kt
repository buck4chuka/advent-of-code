enum class Day01Direction {
    LEFT, RIGHT
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { line ->
            (if (line[0] == 'L') -1 else 1) * line.substring(1).toInt()
        }.runningFold(50) { acc, instruction -> (acc + instruction + 100) % 100 }.count { it == 0 }
    }

    fun part2(input: List<String>): Int {
        return input.map { line ->
            val dir = if (line[0] == 'L') Day01Direction.LEFT else Day01Direction.RIGHT
            val value = line.substring(1).toInt()
            Pair(dir, value)
        }.runningFold(Pair(0,50)) { (_,position), (dir,distance) ->
            var (cycles,remainder) = Pair(distance.div(100), distance.mod(100))
            val signedRemainingDistance = if (dir == Day01Direction.LEFT) -remainder else remainder
            val nextPos = position + signedRemainingDistance
            if (position != 0) {
                if (dir == Day01Direction.LEFT && nextPos <= 0) cycles += 1
                if (dir == Day01Direction.RIGHT && nextPos >= 100) cycles += 1
            }
            Pair(cycles, (nextPos + 100) % 100)
        }.sumOf { it.first }
    }

    val testInput = readInput("year2025/day01/Day01_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 6)

    val input = readInput("year2025/day01/Day01")
    part1(input).println()
    part2(input).println()
}
