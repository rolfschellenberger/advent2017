package com.rolf.day22

import com.rolf.Day
import com.rolf.util.Direction
import com.rolf.util.MatrixString
import com.rolf.util.splitLines

fun main() {
    Day22().run()
}

class Day22 : Day() {
    override fun solve1(lines: List<String>) {
        val grid = MatrixString.build(splitLines(lines))
        grid.grow(300, 300, 300, 300, ".")
        var current = grid.center()
        var direction = Direction.NORTH
        var infected = 0
        for (i in 0 until 10000) {
            when (grid.get(current)) {
                "#" -> {
                    direction = direction.right()
                    grid.set(current, ".")
                }
                "." -> {
                    direction = direction.left()
                    grid.set(current, "#")
                    infected++
                }
            }
            current = when (direction) {
                Direction.NORTH -> grid.getUp(current)!!
                Direction.EAST -> grid.getRight(current)!!
                Direction.SOUTH -> grid.getDown(current)!!
                Direction.WEST -> grid.getLeft(current)!!
            }
        }
        println(infected)
    }

    override fun solve2(lines: List<String>) {
        val grid = MatrixString.build(splitLines(lines))
        grid.grow(300, 300, 300, 300, ".")
        grid.replace(mapOf("." to "c", "#" to "i"))
        var current = grid.center()
        var direction = Direction.NORTH
        var infected = 0
        for (i in 0 until 10000000) {
            when (grid.get(current)) {
                "c" -> {
                    direction = direction.left()
                    grid.set(current, "w")
                }
                "w" -> {
                    grid.set(current, "i")
                    infected++
                }
                "i" -> {
                    direction = direction.right()
                    grid.set(current, "f")
                }
                "f" -> {
                    direction = direction.opposite()
                    grid.set(current, "c")
                }
            }
            current = when (direction) {
                Direction.NORTH -> grid.getUp(current)!!
                Direction.EAST -> grid.getRight(current)!!
                Direction.SOUTH -> grid.getDown(current)!!
                Direction.WEST -> grid.getLeft(current)!!
            }
        }
        println(infected)
    }
}
