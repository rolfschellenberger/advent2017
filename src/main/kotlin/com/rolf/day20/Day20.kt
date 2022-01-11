package com.rolf.day20

import com.rolf.Day
import com.rolf.util.Location
import com.rolf.util.splitLine

fun main() {
    Day20().run()
}

class Day20 : Day() {
    override fun solve1(lines: List<String>) {
        val particles = lines.mapIndexed { index, line -> parseParticle(line, index) }

        // The closest is the one with the lowest acceleration in all directions (sum)
        val zero = Location(0, 0, 0)
        val acceleration = particles.map { it.acceleration.distance(zero) to it }.groupBy { it.first }.toSortedMap()
        val minAcceleration = acceleration[acceleration.firstKey()]?.map { it.second }
        // On a tie, pick the particle already closest to zero
        val distance = minAcceleration?.map { it.position.distance(zero) to it }?.groupBy { it.first }?.toSortedMap()
        println(distance?.get(distance.firstKey())?.map { it.second }?.first()?.id)
    }

    override fun solve2(lines: List<String>) {
        val particles = lines.mapIndexed { index, line -> parseParticle(line, index) }
        val remainder = (1..10000).fold(particles) { acc, _ ->
            acc.map { it.update() }
                .groupBy { it.position }
                .filterValues { it.size == 1 }
                .flatMap { it.value }
        }
        println(remainder.size)
    }

    private fun parseParticle(line: String, id: Int): Particle {
        // p=<-317,1413,1507>, v=<19,-102,-108>, a=<1,-3,-3>
        val (p, v, a) = splitLine(line, ", ")
        return Particle(id, parseLocation(p), parseLocation(v), parseLocation(a))
    }

    private fun parseLocation(input: String): Location {
        val coords = input.substring(3, input.length - 1)
        val (x, y, z) = splitLine(coords, ",").map { it.toInt() }
        return Location(x, y, z)
    }
}

data class Particle(val id: Int, val position: Location, val velocity: Location, val acceleration: Location) {
    fun update(): Particle {
        val v = Location(
            velocity.x + acceleration.x,
            velocity.y + acceleration.y,
            velocity.z + acceleration.z
        )
        val p = Location(
            position.x + v.x,
            position.y + v.y,
            position.z + v.z
        )
        return copy(velocity = v, position = p)
    }
}
