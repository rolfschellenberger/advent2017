package com.rolf.day08

import com.rolf.Day
import com.rolf.util.splitLine

fun main() {
    Day08().run()
}

class Day08 : Day() {
    override fun solve1(lines: List<String>) {
        val instructions = lines.map { parseInstruction(it) }
        val computer = Computer()
        computer.execute(instructions)
        println(computer.registers.values.maxOrNull())
    }

    override fun solve2(lines: List<String>) {
        val instructions = lines.map { parseInstruction(it) }
        val computer = Computer()
        computer.execute(instructions)
        println(computer.highestValue)
    }

    private fun parseInstruction(line: String): Instruction {
        val parts = splitLine(line, " ")
        val register = parts[0]
        val operation = parts[1]
        val value = parts[2].toInt()
        val condition = parts.subList(4, parts.size).joinToString(" ")
        return Instruction(register, operation, value, condition)
    }
}

data class Instruction(val register: String, val operation: String, val value: Int, val condition: String)

class Computer(val registers: MutableMap<String, Int> = mutableMapOf()) {

    var highestValue = 0

    fun execute(instructions: List<Instruction>) {
        for (instruction in instructions) {
            execute(instruction)
        }
    }

    fun execute(instruction: Instruction) {
        if (evaluate(instruction)) {
            when (instruction.operation) {
                "inc" -> setValue(instruction.register, getValue(instruction.register) + instruction.value)
                "dec" -> setValue(instruction.register, getValue(instruction.register) - instruction.value)
                else -> throw Exception("New operation found: ${instruction.operation}")
            }
        }
        highestValue = maxOf(highestValue, registers.values.maxOrNull()!!)
    }

    private fun evaluate(instruction: Instruction): Boolean {
        val (register, operator, valueString) = instruction.condition.split(" ")
        val right = valueString.toInt()
        val left = getValue(register)
        return when (operator) {
            ">" -> left > right
            "<" -> left < right
            ">=" -> left >= right
            "<=" -> left <= right
            "==" -> left == right
            "!=" -> left != right
            else -> throw Exception("Invalid operator: $operator")
        }
    }

    private fun getValue(register: String): Int {
        registers.computeIfAbsent(register) { 0 }
        return registers[register]!!
    }

    private fun setValue(register: String, value: Int) {
        registers[register] = value
    }
}
