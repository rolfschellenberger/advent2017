package com.rolf.day03

import com.rolf.Day
import com.rolf.util.MatrixInt
import com.rolf.util.Point

fun main() {
    Day03().run()
}

class Day03 : Day() {
    override fun solve1(lines: List<String>) {
        val number = lines.first().toInt()

        val grid = MatrixInt.buildDefault(600, 600, 0)
        var location = Point(299, 299)
        var value = 1
        var stepSize = 0
        var iterations = 0

        // Starting point
        grid.set(location, value++)
        location = grid.getRight(location)!!

        var direction = 'R'
        while (value <= number) {
            for (i in 0 until stepSize) {
                grid.set(location, value++)
                location = nextLocation(direction, location, grid)
            }
            grid.set(location, value++)
            direction = nextDirection(direction)
            location = nextLocation(direction, location, grid)

            iterations++
            if (iterations > 1) {
                iterations = 0
                stepSize++
            }
        }

        for (point in grid.allPoints()) {
            if (grid.get(point) == number) {
                println(point.distance(Point(299, 299)))
            }
        }
    }

    private fun nextLocation(direction: Char, location: Point, grid: MatrixInt): Point {
        return when (direction) {
            'R' -> grid.getRight(location)!!
            'L' -> grid.getLeft(location)!!
            'U' -> grid.getUp(location)!!
            'D' -> grid.getDown(location)!!
            else -> throw Exception("Unknown direction $direction")
        }
    }

    private fun nextDirection(direction: Char): Char {
        return when (direction) {
            'R' -> 'U'
            'U' -> 'L'
            'L' -> 'D'
            'D' -> 'R'
            else -> throw Exception("Unknown direction $direction")
        }
    }

    override fun solve2(lines: List<String>) {
        val number = lines.first().toInt()

        val grid = MatrixInt.buildDefault(600, 600, 0)
        var location = Point(299, 299)
        var stepSize = 0
        var iterations = 0

        // Starting point
        grid.set(location, 1)
        location = grid.getRight(location)!!

        var direction = 'R'
        while (true) {
            for (i in 0 until stepSize) {
                val sum = sumNeighbours(grid, location)
                if (sum > number) {
                    println(sum)
                    return
                }
                grid.set(location, sum)
                location = nextLocation(direction, location, grid)
            }
            val sum = sumNeighbours(grid, location)
            if (sum > number) {
                println(sum)
                return
            }
            grid.set(location, sum)
            direction = nextDirection(direction)
            location = nextLocation(direction, location, grid)

            iterations++
            if (iterations > 1) {
                iterations = 0
                stepSize++
            }
        }
    }

    private fun sumNeighbours(grid: MatrixInt, location: Point): Int {
        return grid.getNeighbours(location).map { grid.get(it) }.sum()
    }
}
