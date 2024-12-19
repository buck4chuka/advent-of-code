package year2024.day17

import println
import readInput
import kotlin.math.pow

//Combo operands 0 through 3 represent literal values 0 through 3.
//Combo operand 4 represents the value of register A.
//Combo operand 5 represents the value of register B.
//Combo operand 6 represents the value of register C.
//Combo operand 7 is reserved and will not appear in valid programs.

data class Instruction(val opcode:Int, val operand:Int)



fun part1(input: List<Instruction>, registersInput: Map<Int, Long>): String {
    val registers = registersInput.toMutableMap()
    val output = mutableListOf<Long>()

    fun combo(operand: Int): Long = if (operand <= 3) operand.toLong() else registers[operand]!!

    var pointer = 0

    while (pointer < input.size) {
        val (opcode, operand) = input[pointer]

        when (opcode) {
            0 -> registers[4] =
                (registers[4]!! / 2.0.pow(combo(operand).toDouble())).toLong()
            1 -> registers[5] = registers[5]!! xor operand.toLong()
            2 -> registers[5] = combo(operand) % 8
            3 -> if (registers[4] != 0L) {
                pointer = operand
                continue
            }
            4 -> registers[5] = registers[5]!! xor registers[6]!!
            5 -> output.add(combo(operand) % 8)
            6 -> registers[5] =
                (registers[4]!! / 2.0.pow(combo(operand).toDouble())).toLong()
            7 -> registers[6] =
                (registers[4]!! / 2.0.pow(combo(operand).toDouble())).toLong()
        }
        pointer++
    }

    return output.joinToString(",")
}

fun part2(input: List<String>): Int {

return 0
}




fun main() {
    val input = readInput("year2024/day17/sample")

    val registersInput = input.filter { it.contains("Register") }
        .map { it.split("Register\\s+".toRegex()).last() }.associate {
            val (register, value) = it.split(':')
            val registerNum = when (register[0]) {
                'A' -> 4
                'B' -> 5
                'C' -> 6
                else -> 0
            }
            registerNum to value.trim().toLong()
        }
    val instructions = input
        .asSequence()
        .filter { it.contains("Program") }
        .map { it.split(":").last().trim() }
        .flatMap { it.split(",").map { it.toInt() } }
        .chunked(2)
        .map { (a, b) -> Instruction(a, b) }
        .toList()


    part1(instructions,registersInput).println()
    part2(input).println()
}

