package com.rolf.day12

import com.rolf.Day
import com.rolf.util.EdgeType
import com.rolf.util.Graph
import com.rolf.util.Vertex

fun main() {
    Day12().run()
}

class Day12 : Day() {
    override fun solve1(lines: List<String>) {
        val connections = lines.map { parseConnection(it) }
        val graph = buildGraph(connections)
        val from = graph.vertices().map { it.id }.toMutableList()
        println(findConnections(graph, from, "0").size)
    }

    override fun solve2(lines: List<String>) {
        val connections = lines.map { parseConnection(it) }
        val graph = buildGraph(connections)
        val todo = graph.vertices().map { it.id }.toMutableList()

        var groupCount = 0
        while (todo.isNotEmpty()) {
            val next = todo.removeAt(0)
            val connected = findConnections(graph, todo, next)
            groupCount++
            todo.removeAll(connected)
        }
        println(groupCount)
    }

    private fun findConnections(graph: Graph<Int>, from: MutableList<String>, to: String): List<String> {
        val group = mutableSetOf<String>()
        for (vertex in from) {
            val pair = graph.shortestPathAndWeight(vertex, to)
            group.addAll(pair.first)
        }
        return group.toList()
    }

    private fun buildGraph(connections: List<Connection>): Graph<Int> {
        val graph = Graph<Int>()
        for (connection in connections) {
            graph.addVertex(Vertex(connection.from.toString()))
        }
        for (connection in connections) {
            for (destination in connection.to) {
                graph.addEdge(connection.from.toString(), destination.toString(), EdgeType.UNDIRECTED)
            }
        }
        return graph
    }

    private fun parseConnection(line: String): Connection {
        val (fromString, to) = line.split(" <-> ")
        val from = fromString.toInt()
        val toList = to.split(", ").map { it.toInt() }
        return Connection(from, toList)
    }
}

data class Connection(val from: Int, val to: List<Int>)
