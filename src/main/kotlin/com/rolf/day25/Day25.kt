package com.rolf.day25

import com.rolf.Day

fun main() {
    Day25().run()
}

class Day25 : Day() {
    private val turing = Turing()
    private var state = "A"

    override fun solve1(lines: List<String>) {
        val stateA = State(
            "A", listOf(
                StateValue(0, 1, Move.RIGHT, "B"),
                StateValue(1, 0, Move.LEFT, "C")
            )
        )
        val stateB = State(
            "B", listOf(
                StateValue(0, 1, Move.LEFT, "A"),
                StateValue(1, 1, Move.RIGHT, "D")
            )
        )
        val stateC = State(
            "C", listOf(
                StateValue(0, 0, Move.LEFT, "B"),
                StateValue(1, 0, Move.LEFT, "E")
            )
        )
        val stateD = State(
            "D", listOf(
                StateValue(0, 1, Move.RIGHT, "A"),
                StateValue(1, 0, Move.RIGHT, "B")
            )
        )
        val stateE = State(
            "E", listOf(
                StateValue(0, 1, Move.LEFT, "F"),
                StateValue(1, 1, Move.LEFT, "C")
            )
        )
        val stateF = State(
            "F", listOf(
                StateValue(0, 1, Move.RIGHT, "D"),
                StateValue(1, 1, Move.RIGHT, "A")
            )
        )
        val states = listOf(stateA, stateB, stateC, stateD, stateE, stateF).map { it.name to it }.toMap()

        for (i in 0 until 12667664) {
            turingStep(states)
        }
        println(turing.tape.values.sum())
    }

    private fun turingStep(states: Map<String, State>) {
        val rule = states.getOrElse(state) { throw Exception("Unknown state: $state") }
        val currentValue = turing.getValue()
        val value = rule.values.first { it.currentValue == currentValue }
        turing.setValue(value.newValue)
        turing.move(value.move)
        state = value.nextStateName
    }

    override fun solve2(lines: List<String>) {
    }
}

class Turing(val defaultValue: Int = 0) {
    var position: Int = 0
    val tape: MutableMap<Int, Int> = mutableMapOf()

    fun getValue(): Int {
        return getValue(position)
    }

    fun getValue(pos: Int): Int {
        return tape.computeIfAbsent(pos) { defaultValue }
    }

    fun setValue(value: Int) {
        setValue(position, value)
    }

    fun setValue(pos: Int, value: Int) {
        tape[pos] = value
    }

    fun move(move: Move) {
        when (move) {
            Move.LEFT -> position--
            Move.RIGHT -> position++
        }
    }

    override fun toString(): String {
        return "Turing(position=$position, tape=$tape)"
    }
}

data class State(val name: String, val values: List<StateValue>)

data class StateValue(val currentValue: Int, val newValue: Int, val move: Move, val nextStateName: String)

enum class Move {
    LEFT,
    RIGHT
}
