package com.rolf.day07

import com.rolf.Day
import com.rolf.util.Graph
import com.rolf.util.Vertex
import com.rolf.util.splitLine

fun main() {
    Day07().run()
}

class Day07 : Day() {
    override fun solve1(lines: List<String>) {
        val input: List<Input> = lines.map { parseInput(it) }
        val graph = buildGraph(input)
        val root = graph.getRootVertex()
        println(root!!.id)
    }

    override fun solve2(lines: List<String>) {
        val input: List<Input> = lines.map { parseInput(it) }
        val graph = buildGraph(input)
        val root = graph.getRootVertex()
        findUnbalancedNode(graph, root!!.id)
    }

    private fun parseInput(line: String): Input {
        val parts = splitLine(line, " ")
        val children = if (parts.size > 2) {
            parts.subList(3, parts.size).map { it.removeSuffix(",") }
        } else {
            emptyList()
        }
        return Input(
            parts[0],
            parts[1].removePrefix("(").removeSuffix(")").toInt(),
            children
        )
    }

    private fun buildGraph(input: List<Input>): Graph<Int> {
        val graph = Graph<Int>()
        val roots = mutableSetOf<String>()
        for (i in input) {
            graph.addVertex(Vertex(i.name, weight = i.weight.toDouble()))
            if (i.children.isNotEmpty()) {
                roots.add(i.name)
            }
        }
        for (i in input) {
            for (c in i.children) {
                graph.addEdge(i.name, c)
                roots.remove(c)
            }
        }
        return graph
    }

    private fun findUnbalancedNode(graph: Graph<Int>, id: String) {
        val weights = getChildWeights(graph, id)
        val child = findUnbalancedChild(weights)

        try {
            findUnbalancedNode(graph, child)
        } catch (e: Exception) {
            // We found the unbalanced child
            val diff = calculateDiff(weights, child)
            println((graph.getVertex(child)!!.weight - diff).toInt())
        }
    }


    private fun getChildWeights(graph: Graph<Int>, id: String): MutableMap<String, Double> {
        val edges = graph.edges(id)
        val weights = mutableMapOf<String, Double>()
        for (edge in edges) {
            val weight = graph.getWeight(edge.destination)
            weights[edge.destination] = weight
        }
        return weights
    }

    private fun findUnbalancedChild(weights: MutableMap<String, Double>): String {
        // map to double with list of ids
        val mapByWeight = weights.map { it.value to it.key }.groupBy { it.first }
        for (entry in mapByWeight) {
            if (entry.value.size == 1) {
                return entry.value.first().second
            }
        }
        throw Exception("No unbalanced weights found")
    }

    private fun calculateDiff(weights: MutableMap<String, Double>, child: String): Double {
        var one = 0.0
        var two = 0.0
        for (weight in weights) {
            if (weight.key == child) {
                one = weight.value
            } else {
                two = weight.value
            }
        }
        return one - two
    }
}

data class Input(val name: String, val weight: Int, val children: List<String>)
