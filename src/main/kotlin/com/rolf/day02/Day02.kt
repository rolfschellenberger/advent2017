package com.rolf.day02

import com.rolf.Day
import com.rolf.util.getCombinations
import com.rolf.util.splitLines

fun main() {
    Day02().run()
}

class Day02 : Day() {
    override fun solve1(lines: List<String>) {
        val rows = splitLines(lines, pattern = "\\s+".toPattern()).map { it.map { s -> s.toInt() } }
        println(rows.map { it.maxOrNull()!! - it.minOrNull()!! }.sum())
    }

    override fun solve2(lines: List<String>) {
        val rows = splitLines(lines, pattern = "\\s+".toPattern()).map { it.map { s -> s.toInt() } }
        var sum = 0
        for (row in rows) {
            val combination = getCombinations(row)
                .asSequence()
                .filter { it.size == 2 }
                .map { it.sorted() }
                .filter { it.last() % it.first() == 0 }
                .map { it.last() / it.first() }
                .first()
            sum += combination
        }
        println(sum)
    }
}
