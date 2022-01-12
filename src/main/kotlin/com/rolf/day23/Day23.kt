package com.rolf.day23

import com.rolf.Day
import com.rolf.util.Computer
import com.rolf.util.Instruction
import com.rolf.util.isPrime

fun main() {
    Day23().run()
}

class Day23 : Day() {
    override fun solve1(lines: List<String>) {
        val instructions = lines.map { Instruction(it) }
        val computer = MyComputer(instructions)
        computer.execute()
        println(computer.mulCount)
    }

    override fun solve2(lines: List<String>) {
        val a = lines.first().split(" ")[2].toInt() * 100 + 100000
        val count = (a..a + 17000 step 17).count {
            !it.isPrime()
        }
        println(count)
    }
}

class MyComputer(instructions: List<Instruction>) : Computer(instructions) {
    var mulCount = 0

    override fun execute(instruction: Instruction) {
        when (instruction.operator) {
            "set" -> {
                setValue(instruction[0], instruction[1])
            }
            "sub" -> {
                setValue(instruction[0], getValue(instruction[0]) - getValue(instruction[1]))
            }
            "mul" -> {
                setValue(instruction[0], getValue(instruction[0]) * getValue(instruction[1]))
                mulCount++
            }
            "jnz" -> {
                val x = getValue(instruction[0])
                if (x != 0L) {
                    pointer += getValue(instruction[1]).toInt() - 1
                }
            }
        }
        pointer++
    }
}
