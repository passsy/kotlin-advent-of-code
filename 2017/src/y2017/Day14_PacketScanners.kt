package y2017

import com.pascalwelsch.aoc.challenge
import y2017.Day14_PacketScanners.part1
import y2017.Day14_PacketScanners.part2

fun main(args: Array<String>) {
    part1()
    part2()
}

object Day14_PacketScanners {

    val part1 = challenge("Day 14 - Part One") {
        inputFile("2017/13.txt")
        solveMultiLine {
            val layers = it.map { parseLayer(it) }
            result = severity(layers)
        }
    }

    fun severity(layers: List<Layer>): Int {
        val maxLayer = layers.map { it.depth }.max()!!

        var severity = 0
        for (clock in 0..maxLayer) {
            val layer = layers.firstOrNull { it.depth == clock } ?: continue

            if (clock % (layer.range * 2 - 2) == 0) {
                // caught
                severity += layer.depth * layer.range
            }
        }
        return severity
    }

    fun parseLayer(input: String): Layer {
        val (_, depth, range) = "(\\d+): (\\d+)".toRegex().find(input)?.groupValues
                ?: throw IllegalArgumentException("Input format not valid: '$input'")
        return Layer(depth.toInt(), range.toInt())
    }

    data class Layer(val depth: Int, val range: Int)

    val part2 = challenge("Day 14 - Part Two") {
        inputFile("2017/13.txt")
        solveMultiLine {
            val layers = it.map { parseLayer(it) }
            result = fastestPass(layers)
        }
    }

    fun fastestPass(layers: List<Layer>): Int {
        val maxLayer = layers.map { it.depth }.max()!!

        var offset = 0
        while (true) {
            try {
                for (position in 0..maxLayer) {
                    val layer = layers.firstOrNull { it.depth == position } ?: continue

                    val clock = position + offset

                    if (clock % (layer.range * 2 - 2) == 0) {
                        // caught
                        throw CaughtException()
                    }
                }
                // save pass
                return offset

            } catch (e: CaughtException) {
                // next round
                offset++
            }
        }
    }

    class CaughtException : Throwable()
}