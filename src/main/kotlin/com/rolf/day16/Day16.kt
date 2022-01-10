package com.rolf.day1

import com.rolf.Day
import com.rolf.util.splitLine
import com.rolf.util.swap

fun main() {
    Day16().run()
}

class Day16 : Day() {
    override fun solve1(lines: List<String>) {
        val moves = splitLine(lines.first(), ",")
        var data = ('a'..'p').toList().toCharArray()
        data = dance(moves, data)
        println(data.toList().joinToString(""))
    }

    override fun solve2(lines: List<String>) {
        val moves = splitLine(lines.first(), ",")
        var data = ('a'..'p').toList().toCharArray()
        val original = ('a'..'p').toList().toCharArray()

        // Find the repetition in the dance
        var diff = 0
        for (i in 0 until 1_000_000_000) {
            data = dance(moves, data)
            if (data.contentEquals(original)) {
                diff = i + 1
                break
            }
        }

        // Skip to the last cycle close to the 1 billion to only do the last dance cycles
        val cyclesToGo = 1_000_000_000 % diff
        for (i in 0 until cyclesToGo) {
            data = dance(moves, data)
        }
        println(data.toList().joinToString(""))
    }

    private fun dance(moves: List<String>, data: CharArray): CharArray {
        var data1 = data
        for (move in moves) {
            val operator = splitLine(move).first()
            val parts = move.removePrefix(operator).split("/")
            when (operator) {
                "s" -> {
                    val length = parts[0].toInt()
                    data1 = (data1.takeLast(length) + data1.dropLast(length)).toCharArray()
                }
                "x" -> {
                    data1.swap(parts[0].toInt(), parts[1].toInt())
                }
                "p" -> {
                    data1.swap(data1.indexOf(parts[0].first()), data1.indexOf(parts[1].first()))
                }
                else -> throw Exception("Unknown move: $move")
            }
        }
        return data1
    }
}
