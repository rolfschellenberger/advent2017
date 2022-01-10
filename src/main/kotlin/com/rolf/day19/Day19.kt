package com.rolf.day19

import com.rolf.Day
import com.rolf.util.MatrixString
import com.rolf.util.Point
import com.rolf.util.splitLines

fun main() {
    Day19().run()
}

class Day19 : Day() {
    override fun solve1(lines: List<String>) {
        val maxWidth = lines.map { it.length }.maxOrNull()!!
        val input = lines.map { it.padEnd(maxWidth, ' ') }
        val grid = MatrixString.build(splitLines(input))

        val start = findStart(grid)
        var previous = start
        var current = Point(start.x, start.y + 1)
        val found = mutableListOf<String>()
        var steps = 1
        while (true) {
            steps++
            val value = grid.get(current)
            if (!setOf("-", "|", "+").contains(value)) {
                found.add(value)
            }
            val next = getNext(grid, previous, current)
            if (next == null || grid.get(next) == " ") {
                break
            }
            previous = current
            current = next
        }
        println(found.joinToString(""))
        println(steps)
    }

    override fun solve2(lines: List<String>) {
    }

    private fun findStart(grid: MatrixString): Point {
        for (x in 0 until grid.width()) {
            val value = grid.get(x, 0)
            if (value == "|") {
                return Point(x, 0)
            }
        }
        throw Exception("No start found")
    }

    private fun getNext(grid: MatrixString, previous: Point, current: Point): Point? {
        // When +, go to a neighbour, not previous
        val value = grid.get(current)
        if (value == "+") {
            for (neighbour in grid.getNeighbours(current, diagonal = false)) {
                if (neighbour != previous) {
                    val next = grid.get(neighbour)
                    if (next != " ") {
                        return neighbour
                    }
                }
            }
        }
        // Otherwise continue in same direction
        if (grid.getLeft(current) == previous) {
            return grid.getRight(current)
        }
        if (grid.getRight(current) == previous) {
            return grid.getLeft(current)
        }
        if (grid.getUp(current) == previous) {
            return grid.getDown(current)
        }
        if (grid.getDown(current) == previous) {
            return grid.getUp(current)
        }
        throw Exception("No next node found for $current")
    }
}
