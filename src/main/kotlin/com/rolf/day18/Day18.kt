package com.rolf.day18

import com.rolf.Day
import com.rolf.util.Computer
import com.rolf.util.Instruction

fun main() {
    Day18().run()
}

class Day18 : Day() {
    override fun solve1(lines: List<String>) {
        val instructions = lines.map { Instruction(it) }
        val computer = MyComputer(instructions)
        computer.execute()
        println(computer.recover)
    }

    override fun solve2(lines: List<String>) {
        val instructions = lines.map { Instruction(it) }
        val computer0 = MyComputer2(instructions)
        computer0.setValue("p", 0)
        val computer1 = MyComputer2(instructions)
        computer1.setValue("p", 1)
        computer0.sendTo = computer1
        computer1.sendTo = computer0
        while (true) {
            val before = computer0.steps + computer1.steps
            if (!computer0.isDone()) {
                computer0.executeNext()
            }
            if (!computer1.isDone()) {
                computer1.executeNext()
            }
            val after = computer0.steps + computer1.steps
            // Deadlock!
            if (before == after) {
                break
            }
        }
        println(computer1.sends)
    }
}

class MyComputer(instructions: List<Instruction>) : Computer(instructions) {
    var lastSound: Long = -1L
    var recover: Long = -1L

    override fun execute(instruction: Instruction) {
        when (instruction.operator) {
            "snd" -> {
                lastSound = getValue(instruction[0])
            }
            "set" -> {
                setValue(instruction[0], instruction[1])
            }
            "add" -> {
                setValue(instruction[0], getValue(instruction[0]) + getValue(instruction[1]))
            }
            "mul" -> {
                setValue(instruction[0], getValue(instruction[0]) * getValue(instruction[1]))
            }
            "mod" -> {
                setValue(instruction[0], getValue(instruction[0]) % getValue(instruction[1]))
            }
            "rcv" -> {
                val x = getValue(instruction[0])
                if (x != 0L) {
                    recover = lastSound
                    shouldStop = true
                }
            }
            "jgz" -> {
                val x = getValue(instruction[0])
                if (x > 0) {
                    pointer += getValue(instruction[1]).toInt() - 1
                }
            }
            else -> throw Exception("Unknown operator found: $instruction")
        }
        pointer++
    }
}

class MyComputer2(instructions: List<Instruction>) : Computer(instructions) {
    var sendTo: MyComputer2? = null
    var sends = 0
    val received = mutableListOf<Long>()
    var steps = 0

    override fun execute(instruction: Instruction) {
        when (instruction.operator) {
            "snd" -> {
                sendTo?.received?.add(getValue(instruction[0]))
                sends++
                pointer++
                steps++
            }
            "set" -> {
                setValue(instruction[0], instruction[1])
                pointer++
                steps++
            }
            "add" -> {
                setValue(instruction[0], getValue(instruction[0]) + getValue(instruction[1]))
                pointer++
                steps++
            }
            "mul" -> {
                setValue(instruction[0], getValue(instruction[0]) * getValue(instruction[1]))
                pointer++
                steps++
            }
            "mod" -> {
                setValue(instruction[0], getValue(instruction[0]) % getValue(instruction[1]))
                pointer++
                steps++
            }
            "rcv" -> {
                if (received.isNotEmpty()) {
                    val x = received.removeAt(0)
                    setValue(instruction[0], x)
                    pointer++
                    steps++
                }
            }
            "jgz" -> {
                val x = getValue(instruction[0])
                if (x > 0) {
                    pointer += getValue(instruction[1]).toInt()
                } else {
                    pointer++
                }
                steps++
            }
            else -> throw Exception("Unknown operator found: $instruction")
        }
    }
}
