package com.rolf.day14

import com.rolf.Day
import com.rolf.util.Binary
import com.rolf.util.MatrixInt
import com.rolf.util.Point
import com.rolf.util.splitLines
import java.math.BigInteger

fun main() {
    Day14().run()
}

class Day14 : Day() {
    override fun solve1(lines: List<String>) {
        val hash = lines.first()
        val gridLines = getLines(hash)
        println(gridLines.map { it.count { char -> char == '1' } }.sum())
    }

    override fun solve2(lines: List<String>) {
        val hash = lines.first()
        val gridLines = getLines(hash)
        val grid = MatrixInt.build(splitLines(gridLines))
        grid.replace(mapOf(0 to 8, 1 to 0))

        var groupCount = 0
        for (point in grid.allPoints()) {
            // Iterate groups that haven't been marked before
            if (grid.get(point) == 0) {
                markGroup(grid, point)
                groupCount++
            }
        }
        println(groupCount)
    }

    private fun markGroup(grid: MatrixInt, point: Point) {
        val value = grid.get(point)
        if (value == 0) {
            grid.set(point, 1)
            for (neighbour in grid.getNeighbours(point, diagonal = false)) {
                markGroup(grid, neighbour)
            }
        }
    }

    private fun getLines(hash: String): List<String> {
        val result = mutableListOf<String>()
        for (i in 0 until 128) {
            val knotHash = getKnotHash("$hash-$i")
            val binary = knotHash.map { toBinary(it).toString(true) }.joinToString("")
            result.add(binary)
        }
        return result
    }

    private fun getKnotHash(input: String): String {
        val numbers = input.map { it.code } + listOf(17, 31, 73, 47, 23)
        val list = IntArray(256) { i -> i }
        var position = 0
        var skipSize = 0
        for (n in 0 until 64) {
            for (length in numbers) {
                val sublist = getSubList(list, position, length).reversed()
                placeBack(list, sublist, position)
                position += length + skipSize
                skipSize++
            }
        }

        val sb = StringBuffer()
        for (b in 0 until 16) {
            var segmentValue = 0
            for (s in 0 until 16) {
                segmentValue = segmentValue.xor(list[b * 16 + s])
            }
            val hex = BigInteger.valueOf(segmentValue.toLong()).toString(16).padStart(2, '0')
            sb.append(hex)
        }
        return sb.toString()
    }

    private fun placeBack(list: IntArray, sublist: List<Int>, position: Int) {
        for (i in sublist.indices) {
            list[(position + i) % list.size] = sublist[i]
        }
    }

    private fun getSubList(list: IntArray, position: Int, length: Int): List<Int> {
        val result = mutableListOf<Int>()
        for (i in position until position + length) {
            result.add(list[i % list.size])
        }
        return result
    }

    private fun toBinary(char: Char): Binary {
        return Binary(Integer.parseInt(char.toString(), 16).toLong(), 4)
    }
}
