package com.rolf.day11

import com.rolf.Day
import com.rolf.util.splitLine
import kotlin.math.abs

fun main() {
    Day11().run()
}

class Day11 : Day() {
    private var maxX = 0
    private var maxY = 0
    private var x = 0
    private var y = 0

    override fun solve1(lines: List<String>) {
        val directions = splitLine(lines.first(), ",")
        followDirections(directions)
        println(calculateDistance(x, y))
    }

    override fun solve2(lines: List<String>) {
        println(calculateDistance(maxX, maxY))
    }

    private fun followDirections(directions: List<String>) {
        maxX = 0
        maxY = 0
        x = 0
        y = 0
        for (direction in directions) {
            when (direction) {
                "n" -> y += 2
                "s" -> y -= 2
                "ne" -> {
                    y++
                    x++
                }
                "nw" -> {
                    y++
                    x--
                }
                "se" -> {
                    y--
                    x++
                }
                "sw" -> {
                    y--
                    x--
                }
                else -> throw Exception("Unknown direction $direction")
            }
            maxX = maxOf(maxX, x)
            maxY = maxOf(maxY, y)
        }
    }

    private fun calculateDistance(xIn: Int, yIn: Int): Int {
        var x = abs(xIn)
        var y = abs(yIn)
        val min = minOf(x, y)
        x -= min
        y -= min
        return min + (x + 0.5 * y).toInt()
    }
}
