package y2017

import com.pascalwelsch.aoc.challenge
import y2017.Day19_SeriesOfTubes.Orientation.*
import y2017.Day19_SeriesOfTubes.part1
import y2017.Day19_SeriesOfTubes.part2


fun main(args: Array<String>) {
    part1()
    part2()
}


object Day19_SeriesOfTubes {


    val part1 = challenge("Day 19 - Part One") {
        inputFile("2017/19.txt")
        solveMultiLine {
            result = followRoutingDiagram(it.map { it.toList() }).letters
        }
    }

    data class RouteResult(val steps: Int, val letters: String)

    fun followRoutingDiagram(graph: List<List<Char>>): RouteResult {
        fun Position.char(): Char = graph.getOrNull(y)?.getOrNull(x) ?: ' '
        fun Position.north(): Position = copy(y = y - 1)
        fun Position.east(): Position = copy(x = x + 1)
        fun Position.south(): Position = copy(y = y + 1)
        fun Position.west(): Position = copy(x = x - 1)

        var pos = Position(graph[0].indexOfFirst { !it.isWhitespace() }, 0)
        var orientation = SOUTH

        val letters = mutableListOf<Char>()
        var steps = 0

        fun step() {
            fun next(): Position = when (orientation) {
                NORTH -> pos.north()
                EAST -> pos.east()
                SOUTH -> pos.south()
                WEST -> pos.west()
            }

            fun left(): Position = when (orientation) {
                NORTH -> pos.west()
                EAST -> pos.north()
                SOUTH -> pos.east()
                WEST -> pos.south()
            }

            fun right(): Position = when (orientation) {
                NORTH -> pos.east()
                EAST -> pos.south()
                SOUTH -> pos.west()
                WEST -> pos.north()
            }

            fun moveAndTurn(straightChar: Char,
                            sideChar: Char,
                            leftOrientation: Orientation,
                            rightOrientation: Orientation) {
                val next = next()
                val char = next.char()
                if (char == straightChar || char == sideChar) {
                    pos = next
                    return
                }
                if (char.isLetter()) {
                    // collect
                    letters += char
                    pos = next
                    return
                }
                if (char == '+') {
                    pos = next

                    val left = left().char()
                    if (left == sideChar || left.isLetter()) {
                        orientation = leftOrientation
                        return
                    }
                    val right = right().char()
                    if (right == sideChar || right.isLetter()) {
                        orientation = rightOrientation
                        return
                    }
                }
                if (char == ' ') {
                    // out of bounds
                    pos = Position(-1, -1)
                }
            }

            when (orientation) {
                NORTH -> moveAndTurn('|', '-', WEST, EAST)
                EAST -> moveAndTurn('-', '|', NORTH, SOUTH)
                SOUTH -> moveAndTurn('|', '-', EAST, WEST)
                WEST -> moveAndTurn('-', '|', SOUTH, NORTH)
            }
        }

        while (true) {
            step()
            steps++
            if (pos.outOfBounds(graph)) {
                return RouteResult(steps, letters.joinToString(""))
            }
        }

    }

    data class Position(val x: Int, val y: Int) {
        fun outOfBounds(graph: List<List<Char>>): Boolean {
            val width = graph.map { it.size }.max()!!
            val height = graph.size

            return x !in (0..width) || y !in (0..height)
        }
    }


    enum class Orientation { NORTH, EAST, SOUTH, WEST }

    val part2 = challenge("Day 19 - Part Two") {
        inputFile("2017/19.txt")
        solveMultiLine {
            result = followRoutingDiagram(it.map { it.toList() }).steps
        }
    }
}