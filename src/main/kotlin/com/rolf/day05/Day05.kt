package com.rolf.day05

import com.rolf.Day

fun main() {
    Day05().run()
}

class Day05 : Day() {
    override fun solve1(lines: List<String>) {
        val jumps = lines.map { it.toInt() }.toMutableList()
        var pointer = 0
        var steps = 0
        while (pointer in 0 until jumps.size) {
            val toJump = jumps[pointer]
            jumps[pointer]++
            pointer += toJump
            steps++
        }
        println(steps)
    }

    override fun solve2(lines: List<String>) {
        val jumps = lines.map { it.toInt() }.toMutableList()
        var pointer = 0
        var steps = 0
        while (pointer in 0 until jumps.size) {
            val toJump = jumps[pointer]
            if (toJump >= 3) {
                jumps[pointer]--
            } else {
                jumps[pointer]++
            }
            pointer += toJump
            steps++
        }
        println(steps)
    }

}
