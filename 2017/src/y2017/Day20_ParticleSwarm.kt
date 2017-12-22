package y2017

import com.pascalwelsch.aoc.challenge
import y2017.Day20_ParticleSwarm.part1
import kotlin.math.abs


fun main(args: Array<String>) {
    part1()
}

object Day20_ParticleSwarm {
    val part1 = challenge("Day 20 - Part One") {
        inputFile("2017/20.txt")
        solveMultiLine {
            val particles = it.mapIndexed(::toParticle)
            result = closesParticleLongTerm(particles).id
        }
    }

    fun toParticle(index: Int, input: String): Particle {
        val (_, p, v, a) = "p=<(.*)>, v=<(.*)>, a=<(.*)>".toRegex().find(input)!!.groupValues
        return Particle(index, Vector.of(p), Vector.of(v), Vector.of(a))
    }

    val origin = Vector(0, 0, 0)

    fun closesParticleLongTerm(particles: List<Particle>): Particle {
        return particles.minBy { it.acceleration.distance(origin) }!!
    }

    data class Particle(
            val id: Int,
            val position: Vector,
            val velocity: Vector,
            val acceleration: Vector
    ) {
        fun tick(): Particle {
            val newV = velocity + acceleration
            return Particle(id, position + newV, newV, acceleration)
        }
    }

    data class Vector(val x: Long, val y: Long, val z: Long) {
        companion object {
            fun of(input: String): Vector {
                val (x, y, z) = input.split(",").map(String::toLong)
                return Vector(x, y, z)
            }
        }

        fun distance(origin: Vector): Long {
            return abs(origin.x - x) + abs(origin.y - y) + abs(origin.z - z)
        }

        operator fun plus(other: Vector): Vector = Vector(x + other.x, y + other.y, z + other.z)
    }

    val part2 = challenge("Day 20 - Part Two") {
        inputFile("2017/20.txt")
        solveMultiLine {
            val particles = it.mapIndexed(::toParticle)
            result = leftoversAfterCollisions(particles).count()
        }
    }

    fun leftoversAfterCollisions(all: List<Particle>): List<Particle> {
        var particles = all
        repeat(100) {
            particles = particles.map { it.tick() }
            val colliding = particles.groupBy { it.position }.values
                    .filter { it.size > 1 }
                    .flatten()
            if (colliding.size > 1) {
                colliding.forEach { println("removing ${it.id}") }
                particles -= colliding
            }
        }

        return particles
    }
}