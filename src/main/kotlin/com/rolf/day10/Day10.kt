package com.rolf.day10

import com.rolf.Day
import com.rolf.util.splitLine
import java.math.BigInteger

fun main() {
    Day10().run()
}

class Day10 : Day() {
    override fun solve1(lines: List<String>) {
        val numbers = splitLine(lines.first(), ",").map { it.toInt() }
        val list = IntArray(256) { i -> i }
        var position = 0

        for ((skipSize, length) in numbers.withIndex()) {
            val sublist = getSubList(list, position, length).reversed()
            placeBack(list, sublist, position)
            position += length + skipSize
        }
        println(list[0] * list[1])
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

    override fun solve2(lines: List<String>) {
        val numbers = lines.first().map { it.code } + listOf(17, 31, 73, 47, 23)
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
        println(sb)
    }
}
