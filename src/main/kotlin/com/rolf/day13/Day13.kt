package com.rolf.day13

import com.rolf.Day

fun main() {
    Day13().run()
}

class Day13 : Day() {
    override fun solve1(lines: List<String>) {
        val layers = lines.map { parseLayer(it) }

        val sum = layers
            .filter { it.caughtAt(0) }
            .sumOf { it.severity }
        println(sum)
    }

    override fun solve2(lines: List<String>) {
        val layers = lines.map { parseLayer(it) }

        val firstTime = generateSequence(0, Int::inc)
            .filter { time ->
                layers.none { it.caughtAt(time) }
            }
            .first()
        println(firstTime)
    }

    private fun parseLayer(line: String): Layer {
        val (depth, range) = line.split(": ").map { it.toInt() }
        return Layer(depth, range)
    }
}

data class Layer(val depth: Int, val range: Int, var position: Int = 0) {
    val severity: Int = depth * range

    /**
     * location (depth = 2, range = 4 e.g.)
     * 2 % ((4-1) * 2)) = 2 % 6 = 2
     * location (depth = 3, range = 2 e.g.)
     * 3 % (2-1) * 2)) = 3 % 2 = 1
     * location (depth = 4, range = 2 e.g.)
     * 4 % (2-1) * 2)) = 4 % 2 = 0
     */
    fun caughtAt(time: Int): Boolean {
        return (time + depth) % ((range - 1) * 2) == 0
    }
}
