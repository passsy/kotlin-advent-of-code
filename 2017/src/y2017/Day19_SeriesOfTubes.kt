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

    //--- Day 19: A Series of Tubes ---
    //Somehow, a network packet got lost and ended up here. It's trying to follow a routing diagram (your puzzle input), but it's confused about where to go.
    //
    //Its starting point is just off the top of the diagram. Lines (drawn with |, -, and +) show the path it needs to take, starting by going down onto the only line connected to the top of the diagram. It needs to follow this path until it reaches the end (located somewhere within the diagram) and stop there.
    //
    //Sometimes, the lines cross over each other; in these cases, it needs to continue going the same direction, and only turn left or right when there's no other option. In addition, someone has left letters on the line; these also don't change its direction, but it can use them to keep track of where it's been. For example:
    //
    //|
    //|  +--+
    //A  |  C
    //F---|----E|--+
    //|  |  |  D
    //+B-+  +--+
    //
    //Given this diagram, the packet needs to take the following path:
    //
    //Starting at the only line touching the top of the diagram, it must go down, pass through A, and continue onward to the first +.
    //Travel right, up, and right, passing through B in the process.
    //Continue down (collecting C), right, and up (collecting D).
    //Finally, go all the way left through E and stopping at F.
    //Following the path to the end, the letters it sees on its path are ABCDEF.
    //
    //The little packet looks up at you, hoping you can help it find the way. What letters will it see (in the order it would see them) if it follows the path? (The routing diagram is very wide; make sure you view it without line wrapping.)
    val part1 = challenge("Day 19 - Part One") {
        solve {
            result = followRoutingDiagram(inputFile("2017/19.txt").lines().map { it.toList() }).letters //MKXOIHZNBL
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

            fun moveAndTurn(
                    straightChar: Char,
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

    //--- Part Two ---
    //The packet is curious how many steps it needs to go.
    //
    //For example, using the same routing diagram from the example above...
    //
    //|
    //|  +--+
    //A  |  C
    //F---|--|-E---+
    //|  |  |  D
    //+B-+  +--+
    //
    //...the packet would go:
    //
    //6 steps down (including the first line at the top of the diagram).
    //3 steps right.
    //4 steps up.
    //3 steps right.
    //4 steps down.
    //3 steps right.
    //2 steps up.
    //13 steps left (including the F it stops on).
    //This would result in a total of 38 steps.
    //
    //How many steps does the packet need to go?
    val part2 = challenge("Day 19 - Part Two") {
        solve {
            result = followRoutingDiagram(inputFile("2017/19.txt").lines().map { it.toList() }).steps //17872
        }
    }
}