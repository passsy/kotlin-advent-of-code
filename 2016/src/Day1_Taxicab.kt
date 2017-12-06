import Day1_Taxicab.Orientation.*
import Day1_Taxicab.Rotation.LEFT
import Day1_Taxicab.Rotation.RIGHT
import com.pascalwelsch.aoc.challenge

fun main(args: Array<String>) {
    Day1_Taxicab.part1()
    Day1_Taxicab.part2()
}

object Day1_Taxicab {
    //--- Day 1: No Time for a Taxicab ---
    //
    //Santa's sleigh uses a very high-precision clock to guide its movements, and the clock's oscillator is regulated by stars. Unfortunately, the stars have been stolen... by the Easter Bunny. To save Christmas, Santa needs you to retrieve all fifty stars by December 25th.
    //
    //Collect stars by solving puzzles. Two puzzles will be made available on each day in the advent calendar; the second puzzle is unlocked when you complete the first. Each puzzle grants one star. Good luck!
    //
    //You're airdropped near Easter Bunny Headquarters in a city somewhere. "Near", unfortunately, is as close as you can get - the instructions on the Easter Bunny Recruiting Document the Elves intercepted start here, and nobody had time to work them out further.
    //
    //The Document indicates that you should start at the given coordinates (where you just landed) and face North. Then, follow the provided sequence: either turn left (L) or right (R) 90 degrees, then walk forward the given number of blocks, ending at a new intersection.
    //
    //There's no time to follow such ridiculous instructions on foot, though, so you take a moment and work out the destination. Given that you can only walk on the street grid of the city, how far is the shortest path to the destination?
    //
    //For example:
    //
    //Following R2, L3 leaves you 2 blocks East and 3 blocks North, or 5 blocks away.
    //R2, R2, R2 leaves you 2 blocks due South of your starting position, which is 2 blocks away.
    //R5, L5, R5, R3 leaves you 12 blocks away.
    //How many blocks away is Easter Bunny HQ?
    val part1 = challenge("Day 1 - Part One") {
        inputFile("1.txt")
        solve {
            val position = followInstructions(parseInstructions(it))
            result = position.distance()
        }
    }

    fun parseInstructions(input: String): List<Instruction> {
        return input.split(", ").map { instruction ->
            Instruction(
                    rotate = when (instruction.take(1)) {
                        "R" -> RIGHT
                        "L" -> LEFT
                        else -> throw IllegalStateException("Unknown instruction $instruction")
                    },
                    walkDistance = instruction.drop(1).toInt()
            )
        }
    }

    fun followInstructions(instructions: List<Instruction>): Position {
        var x = 0
        var y = 0
        var orientation = NORTH

        for (move in instructions) {
            orientation = orientation.rotate(move.rotate)

            when (orientation) {
                NORTH -> y += move.walkDistance
                EAST -> x += move.walkDistance
                SOUTH -> y -= move.walkDistance
                WEST -> x -= move.walkDistance
            }
        }
        return Position(x, y)
    }

    data class Instruction(
            val rotate: Rotation,
            val walkDistance: Int
    )

    enum class Rotation { LEFT, RIGHT }
    enum class Orientation { NORTH, EAST, SOUTH, WEST;

        fun rotate(rotation: Rotation): Orientation {
            return when (rotation) {
                LEFT -> when (this) {
                    NORTH -> WEST
                    EAST -> NORTH
                    SOUTH -> EAST
                    WEST -> SOUTH
                }
                RIGHT -> when (this) {
                    NORTH -> EAST
                    EAST -> SOUTH
                    SOUTH -> WEST
                    WEST -> NORTH
                }
            }
        }

    }

    data class Position(val x: Int, val y: Int) {
        fun distance() = Math.abs(x) + Math.abs(y)
    }

    //--- Part Two ---
    //
    //Then, you notice the instructions continue on the back of the Recruiting Document. Easter Bunny HQ is actually at the first location you visit twice.
    //
    //For example, if your instructions are R8, R4, R4, R8, the first location you visit twice is 4 blocks away, due East.
    //
    //How many blocks away is the first location you visit twice?
    val part2 = challenge("Day 1 - Part Two") {
        inputFile("1.txt")
        solve {
            val position = findFirstCrossing(parseInstructions(it))
            result = position.distance()
        }
    }

    fun findFirstCrossing(instructions: List<Instruction>): Position {
        var x = 0
        var y = 0
        var orientation = NORTH

        val recentVisits = mutableSetOf<Position>()

        for (move in instructions) {
            orientation = orientation.rotate(move.rotate)

            (1..move.walkDistance).forEach {
                when (orientation) {
                    NORTH -> y++
                    EAST -> x++
                    SOUTH -> y--
                    WEST -> x--
                }

                val pos = Position(x, y)
                if (recentVisits.contains(pos)) {
                    return pos
                } else {
                    recentVisits.add(pos)
                }
            }
        }
        return Position(x, y)
    }

}