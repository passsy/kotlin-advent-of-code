package y2017

import com.pascalwelsch.aoc.challenge
import y2017.Day11_HexEd.HexDirection.*
import kotlin.math.max

fun main(args: Array<String>) {
    Day10_KnotHash.part1()
}

object Day11_HexEd {

    //--- Day 11: Hex Ed ---
    //
    //Crossing the bridge, you've barely reached the other side of the stream when a program comes up to you, clearly in distress. "It's my child process," she says, "he's gotten lost in an infinite grid!"
    //
    //Fortunately for her, you have plenty of experience with infinite grids.
    //
    //Unfortunately for you, it's a hex grid.
    //
    //The hexagons ("hexes") in this grid are aligned such that adjacent hexes can be found to the north, northeast, southeast, south, southwest, and northwest:
    //
    //\ n  /
    //nw +--+ ne
    ///    \
    //-+      +-
    //\    /
    //sw +--+ se
    /// s  \
    //You have the path the child process took. Starting where he started, you need to determine the fewest number of steps required to reach him. (A "step" means to move from the hex you are in to any adjacent hex.)
    //
    //For example:
    //
    //ne,ne,ne is 3 steps away.
    //ne,ne,sw,sw is 0 steps away (back where you started).
    //ne,ne,s,s is 2 steps away (se,se).
    //se,sw,se,sw,sw is 3 steps away (s,s,sw).
    val part1 = challenge("Day 11 - Part One") {
        inputFile("2017/11.txt")
        solve {
            val directions = it.split(',').map { HexDirection.from(it) }
            val origin = HexPosition(0, 0)
            val position = followDirections(directions, origin)
            result = position.distance(origin)
        }
    }

    fun followDirections(directions: List<HexDirection>, origin: HexPosition): HexPosition {
        return directions.fold(origin, { position, direction -> position + direction })
    }
    
    enum class HexDirection {
        NORTH, NORTH_EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, NORTH_WEST;

        companion object {
            fun from(direction: String): HexDirection = when (direction) {
                "n" -> NORTH
                "ne" -> NORTH_EAST
                "se" -> SOUTH_EAST
                "s" -> SOUTH
                "sw" -> SOUTH_WEST
                "nw" -> NORTH_WEST
                else -> throw IllegalArgumentException("unknown direction $direction")
            }
        }
    }

    data class HexPosition(val x: Int, val y: Int) {
        fun distance(to: HexPosition): Int {
            val dy = to.y - y
            val dx = to.x - x
            val dDiff = Math.abs(dx - dy)
            return Math.max(dDiff, Math.max(Math.abs(dx), Math.abs(dy)))
        }

        operator fun plus(direction: HexDirection): HexPosition = when (direction) {
            NORTH -> HexPosition(x, y + 1)
            NORTH_EAST -> HexPosition(x + 1, y + 1)
            SOUTH_EAST -> HexPosition(x + 1, y)
            SOUTH -> HexPosition(x, y - 1)
            SOUTH_WEST -> HexPosition(x - 1, y - 1)
            NORTH_WEST -> HexPosition(x - 1, y)
        }
    }

    //--- Part Two ---
    //
    //How many steps away is the furthest he ever got from his starting position?
    val part2 = challenge("Day 11 - Part One") {
        inputFile("2017/11.txt")
        solve {
            val directions = it.split(',').map { HexDirection.from(it) }
            val origin = HexPosition(0, 0)
            result = furthestDistance(directions, origin)
        }
    }

    fun furthestDistance(directions: List<HexDirection>, origin: HexPosition): Int {
        var position = origin
        var furthest = 0

        for (direction in directions) {
            position += direction

            val distance = position.distance(origin)
            furthest = max(furthest, distance)
        }

        return furthest
    }

}
