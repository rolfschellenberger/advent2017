package com.rolf.day01

import com.rolf.Day
import com.rolf.util.splitLine

fun main() {
    Day01().run()
}

class Day01 : Day() {
    override fun solve1(lines: List<String>) {
        val numbers = splitLine(lines.first()).map { it.toInt() }
        val circular = numbers + numbers.first()

        val matches = mutableListOf<Int>()
        for (index in 0..circular.size - 2) {
            val number = circular[index]
            if (number == circular[index + 1]) {
                matches.add(number)
            }
        }
        println(matches.sum())
    }

    override fun solve2(lines: List<String>) {
        val circular = splitLine(lines.first()).map { it.toInt() }

        val matches = mutableListOf<Int>()
        for (index in circular.indices) {
            val number = circular[index]
            val half = (index + circular.size / 2) % circular.size
            if (number == circular[half]) {
                matches.add(number)
            }
        }
        println(matches.sum())
    }

}
