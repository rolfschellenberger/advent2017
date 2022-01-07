package com.rolf.day04

import com.rolf.Day
import com.rolf.util.splitLines

fun main() {
    Day04().run()
}

class Day04 : Day() {
    override fun solve1(lines: List<String>) {
        val phrases = splitLines(lines, " ")
        println(
            phrases
                .filter { it.toSet().size == it.size }
                .count()
        )
    }

    override fun solve2(lines: List<String>) {
        val phrases = splitLines(lines, " ")
        println(
            phrases
                .map { list -> list.map { word -> word.toSortedSet() } }
                .filter { it.toSet().size == it.size }
                .count()
        )
    }
}
