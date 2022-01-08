package com.rolf.day09

import com.rolf.Day
import java.util.*

fun main() {
    Day09().run()
}

class Day09 : Day() {
    override fun solve1(lines: List<String>) {
        val input = lines.first()
        val cleaned = removeGarbage(input)
        println(countGroups(cleaned))
    }

    override fun solve2(lines: List<String>) {
        val input = lines.first()
        val garbage = getGarbage(input)
        println(countCharsInGarbage(garbage))
    }

    private fun removeGarbage(input: String): String {
        var index = 0
        var inGarbage = -1
        val garbageRanges = mutableListOf<IntRange>()
        while (index < input.length) {
            val char = input[index]
            when (char) {
                '<' -> if (inGarbage < 0) inGarbage = index
                '>' -> {
                    garbageRanges.add(inGarbage..index)
                    inGarbage = -1
                }
                '!' -> index++
            }

            index++
        }

        var output = input
        for (range in garbageRanges) {
            output = output.replaceRange(range, " ".repeat(range.last - range.first + 1))
        }
        return output.replace(" ", "")
    }

    private fun countGroups(input: String): Int {
        val stack = Stack<Int>()

        var score = 0
        for (char in input) {
            when (char) {
                '{' -> stack.push(stack.size + 1)
                '}' -> score += stack.pop()
            }
        }
        return score
    }

    private fun getGarbage(input: String): List<String> {
        var index = 0
        var inGarbage = -1
        val garbageRanges = mutableListOf<IntRange>()
        while (index < input.length) {
            val char = input[index]
            when (char) {
                '<' -> if (inGarbage < 0) inGarbage = index
                '>' -> {
                    garbageRanges.add(inGarbage..index)
                    inGarbage = -1
                }
                '!' -> index++
            }

            index++
        }

        val garbage = mutableListOf<String>()
        for (range in garbageRanges) {
            garbage.add(input.substring(range))
        }
        return garbage
    }

    private fun countCharsInGarbage(garbage: List<String>): Int {
        var count = 0
        for (part in garbage) {
            count += countChars(part)
        }
        return count
    }

    private fun countChars(part: String): Int {
        var count = 0
        var index = 0
        while (index < part.length) {
            val char = part[index]
            when (char) {
                '!' -> index++
                else -> count++
            }
            index++
        }
        return count - 2 // leading and tailing <>
    }
}
