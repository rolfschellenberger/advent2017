package com.rolf.day24

import com.rolf.Day

fun main() {
    Day24().run()
}

class Day24 : Day() {
    override fun solve1(lines: List<String>) {
        val nodes = lines.map {
            val (a, b) = it.split("/").map { it.toInt() }
            Node(a, b)
        }
        val bridges = buildBridges(nodes)
        println(bridges.map { it.map { it.strength }.sum() }.maxOrNull())
    }

    override fun solve2(lines: List<String>) {
        val nodes = lines.map {
            val (a, b) = it.split("/").map { it.toInt() }
            Node(a, b)
        }
        val bridges = buildBridges(nodes)
        val maxLength = bridges.map { it.size }.maxOrNull()
        println(bridges.filter { it.size == maxLength }.map { it.map { it.strength }.sum() }.maxOrNull())
    }

    private fun buildBridges(nodes: List<Node>, bridge: List<Node> = emptyList(), value: Int = 0): List<List<Node>> {
        val options = nodes.filter { it.fits(value) }
        val result: MutableList<List<Node>> = mutableListOf()
        for (option in options) {
            result.addAll(
                buildBridges(
                    nodes - option,
                    bridge + option,
                    option.other(value)
                )
            )
        }
        if (options.isEmpty()) {
            result.add(bridge)
        }
        return result
    }
}

data class Node(val left: Int, val right: Int) {

    val strength = left + right

    fun fits(value: Int): Boolean {
        return left == value || right == value
    }

    fun other(value: Int): Int {
        return if (left == value) right else left
    }
}
