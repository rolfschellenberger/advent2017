package com.rolf.day21

import com.rolf.Day
import com.rolf.util.*
import kotlin.math.sqrt

fun main() {
    Day21().run()
}

class Day21 : Day() {
    override fun solve1(lines: List<String>) {
        val groups = groupLines(lines, "")
        val mutations = groups[1].map { parseMutation(it) }
        val allMutations = addOptions(mutations)

        var grid = MatrixString.build(splitLines(groups[0]))
        for (i in 0 until 5) {
            grid = nextStep(grid, allMutations)
        }
        println(grid.count("#"))
    }

    override fun solve2(lines: List<String>) {
        val groups = groupLines(lines, "")
        val mutations = groups[1].map { parseMutation(it) }
        val allMutations = addOptions(mutations)

        var grid = MatrixString.build(splitLines(groups[0]))
        for (i in 0 until 18) {
            grid = nextStep(grid, allMutations)
        }
        println(grid.count("#"))
    }

    private fun parseMutation(line: String): Mutation {
        // ../.. => ###/.../#..
        val (fromString, toString) = line.split(" => ")
        return Mutation(parseGraph(fromString), parseGraph(toString))
    }

    private fun parseGraph(input: String): MatrixString {
        val lines = input.split("/")
        return MatrixString.build(splitLines(lines))
    }

    private fun addOptions(mutations: List<Mutation>): List<Mutation> {
        val result: MutableList<Mutation> = mutableListOf()
        for (mutation in mutations) {
            result.addAll(createOptions(mutation))
        }
        return result
    }

    private fun createOptions(mutation: Mutation): List<Mutation> {
        val result: MutableList<Mutation> = mutableListOf()
        val current = mutation.from.copy()

        // Add rotation + flip for each 'from'
        val flip = current.copy()
        flip.flip()
        result.add(Mutation(current.copy(), mutation.to))
        result.add(Mutation(flip, mutation.to))

        for (i in 0 until 3) {
            current.rotateRight()
            val new = current.copy()
            val newFlip = current.copy()
            newFlip.flip()
            result.add(Mutation(new, mutation.to))
            result.add(Mutation(newFlip, mutation.to))
        }
        return result
    }

    private fun nextStep(grid: MatrixString, mutations: List<Mutation>): MatrixString {
        val grids = split(grid)
        val replacements = mutableListOf<MatrixString>()
        for (g in grids) {
            replacements.add(findReplacement(mutations, g))
        }
        return glue(replacements)
    }

    private fun split(grid: MatrixString): List<MatrixString> {
        val result = mutableListOf<MatrixString>()
        val stepSize = if (grid.width().isEven()) 2 else 3
        for (y in 0 until grid.height() step stepSize) {
            for (x in 0 until grid.width() step stepSize) {
                val segment = cutOut(grid, Point(x, y), Point(x + stepSize - 1, y + stepSize - 1))
                result.add(MatrixString(segment))
            }
        }
        return result
    }

    private fun cutOut(
        grid: MatrixString,
        topLeftInclusive: Point,
        bottomRightInclusive: Point
    ): MutableList<MutableList<String>> {
        val rows = mutableListOf<MutableList<String>>()
        for (y in topLeftInclusive.y..bottomRightInclusive.y) {
            val row = mutableListOf<String>()
            for (x in topLeftInclusive.x..bottomRightInclusive.x) {
                row.add(grid.get(x, y))
            }
            rows.add(row)
        }
        return rows
    }

    private fun findReplacement(mutations: List<Mutation>, original: MatrixString): MatrixString {
        for (mutation in mutations) {
            if (mutation.from == original) {
                return mutation.to
            }
        }
        throw Exception("No replacement found for original!")
    }

    private fun glue(replacements: List<MatrixString>): MatrixString {
        val steps = sqrt(replacements.size.toDouble()).toInt()
        val width = replacements.first().width()
        val side = steps * width

        val result = MatrixString.buildDefault(side, side, ".")
        for ((index, replacement) in replacements.withIndex()) {
            val xStart = width * (index % steps)
            val yStart = width * (index / steps)
            for (point in replacement.allPoints()) {
                result.set(xStart + point.x, yStart + point.y, replacement.get(point))
            }
        }
        return result
    }
}

class Mutation(val from: MatrixString, val to: MatrixString)
