package com.rolf.day17

import com.rolf.Day

fun main() {
    Day17().run()
}

class Day17 : Day() {
    override fun solve1(lines: List<String>) {
        val steps = lines.first().toInt()
        var pointer = 0
        val list = mutableListOf(0)
        for (i in 1..2017) {
            pointer = moveSteps(steps, pointer, list.size) + 1
            list.add(pointer, i)
        }
        println(list[pointer + 1])
    }

    private fun moveSteps(steps: Int, pointer: Int, size: Int): Int {
        return (pointer + steps) % size
    }

    override fun solve2(lines: List<String>) {
        val steps = lines.first().toInt()
        var pointer = 0
        var afterZero = 0
        for (i in 1..50_000_000) {
            pointer = moveSteps(steps, pointer, i) + 1
            if (pointer == 1) {
                afterZero = i
            }
        }
        println(afterZero)
    }
}
