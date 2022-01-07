package com.rolf.day06

import com.rolf.Day
import com.rolf.util.splitLine

fun main() {
    Day06().run()
}

class Day06 : Day() {
    override fun solve1(lines: List<String>) {
        val memory = splitLine(lines.first(), pattern = "\\s+".toPattern()).map { it.toInt() }.toMutableList()
        println(cycle(memory))
    }

    private fun cycle(memory: MutableList<Int>): Int {
        val configs = mutableSetOf<String>()
        configs.add(memory.joinToString())
        var cycle = 0
        while (true) {
            cycle++
            val (pointer, value) = getMostBlocks(memory)
            distributeBlocks(memory, pointer, value)

            if (!configs.add(memory.joinToString())) {
                return cycle
            }
        }
    }

    override fun solve2(lines: List<String>) {
        val memory = splitLine(lines.first(), pattern = "\\s+".toPattern()).map { it.toInt() }.toMutableList()
        cycle(memory)
        // Cycle again from duplicate memory state till next
        println(cycle(memory))
    }

    private fun getMostBlocks(memory: List<Int>): List<Int> {
        val max = memory.maxOrNull()!!
        for ((index, block) in memory.withIndex()) {
            if (block == max) {
                return listOf(index, max)
            }
        }
        throw Exception("Something weird happened!")
    }

    private fun distributeBlocks(memory: MutableList<Int>, pointer: Int, value: Int) {
        var pointer1 = pointer
        var value1 = value
        memory[pointer1] = 0
        while (value1 > 0) {
            pointer1 = (pointer1 + 1) % memory.size
            memory[pointer1] += 1
            value1--
        }
    }
}
