package com.rolf.day15

import com.rolf.Day

fun main() {
    Day15().run()
}

class Day15 : Day() {
    override fun solve1(lines: List<String>) {
        println(compareGenerators(lines, 40_000_000))
    }

    override fun solve2(lines: List<String>) {
        println(compareGenerators(lines, 5_000_000, 4, 8))
    }

    private fun compareGenerators(lines: List<String>, times: Int, divA: Int = 1, divB: Int = 1): Int {
        val a = lines[0].split(" ").last().toLong()
        val b = lines[1].split(" ").last().toLong()
        val factorA = 16807L
        val factorB = 48271L
        val generatorA = generator(a, factorA)
        val generatorB = generator(b, factorB)
        return generatorA.filter { it % divA == 0 }
            .zip(generatorB.filter { it % divB == 0 })
            .take(times)
            .filter { it.first == it.second }
            .count()
    }

    private fun generator(start: Long, factor: Long, divisor: Long = 2147483647): Sequence<Short> {
        return generateSequence((start * factor) % divisor) { past ->
            (past * factor) % divisor
        }.map { it.toShort() }
    }
}
