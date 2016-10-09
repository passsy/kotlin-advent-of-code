package y2015.day6

import common.solveFromInput


fun main(args: Array<String>) {

    solveFromInput("day6-1") { input, output ->
        val result = litLights(input, 1000)
        output.write("$result")
    }

    solveFromInput("day6-2") { input, output ->
        val result = litLightsWithBrightness(input, 1000)
        output.write("$result")
    }
}

/**
 * --- Day 6: Probably a Fire Hazard ---

Because your neighbors keep defeating you in the holiday house decorating contest year after year, you've decided to deploy one million lights in a 1000x1000 grid.

Furthermore, because you've been especially nice this year, Santa has mailed you instructions on how to display the ideal lighting configuration.

Lights in your grid are numbered from 0 to 999 in each direction; the lights at each corner are at 0,0, 0,999, 999,999, and 999,0. The instructions include whether to turn on, turn off, or toggle various inclusive ranges given as coordinate pairs. Each coordinate pair represents opposite corners of a rectangle, inclusive; a coordinate pair like 0,0 through 2,2 therefore refers to 9 lights in a 3x3 square. The lights all start turned off.

To defeat your neighbors this year, all you have to do is set up your lights by doing the instructions Santa sent you in order.

For example:

turn on 0,0 through 999,999 would turn on (or leave on) every light.
toggle 0,0 through 999,0 would toggle the first line of 1000 lights, turning off the ones that were on, and turning on the ones that were off.
turn off 499,499 through 500,500 would turn off (or leave off) the middle four lights.
After following the instructions, how many lights are lit?
 */
fun litLights(instructions: List<String>, gridSize: Int): Int {

    val grid = LightGrid(gridSize)

    instructions.forEach {
        val instruction = parseInstruction(it)

        grid.setRegion(instruction.region) { level ->
            when (instruction.action) {
                Action.TURN_ON -> 1
                Action.TURN_OFF -> 0
                Action.TOGGLE -> if (level == 1) 0 else 1
            }
        }
    }

    return grid.litCount()
}

fun parseInstruction(instruction: String): Instruction {
    val regex = "(.*)\\s(\\d+),(\\d+)\\sthrough\\s(\\d+),(\\d+)".toRegex()
    try {
        val (all, command, fromX, fromY, toX, toY) = regex.find(instruction)!!.groupValues
        val region = GridRegion(fromX.toInt(), fromY.toInt(), toX.toInt(), toY.toInt())
        val action: Action = when (command) {
            "turn on" -> Action.TURN_ON
            "toggle" -> Action.TOGGLE
            "turn off" -> Action.TURN_OFF
            else -> throw IllegalArgumentException("unknown command")
        }

        return Instruction(action, region)

    } catch (e: NullPointerException) {
        throw IllegalArgumentException("instruction '$instruction' doesn't fit the known format")
    }

}

enum class Action {
    TURN_ON,
    TURN_OFF,
    TOGGLE
}

data class Instruction(
        val action: Action,
        val region: GridRegion
)

class LightGrid(val size: Int) {

    val grid = Array(size) { Array(size) { 0 } }

    fun setRegion(region: GridRegion, setter: (Int) -> Int) {
        for (i in region.fromX..region.toX) {
            for (j in region.fromY..region.toY) {
                grid[i][j] = setter(grid[i][j])
            }
        }
    }

    fun litCount(): Int {
        var count = 0
        for (i in 0..size - 1) {
            for (j in 0..size - 1) {
                count += grid[i][j]
            }
        }
        return count
    }
}

data class GridRegion(
        val fromX: Int,
        val fromY: Int,
        val toX: Int,
        val toY: Int
)

private operator fun <E> List<E>.component6(): E = get(5)

/*
--- Part Two ---

You just finish implementing your winning light pattern when you realize you mistranslated Santa's message from Ancient Nordic Elvish.

The light grid you bought actually has individual brightness controls; each light can have a brightness of zero or more. The lights all start at zero.

The phrase turn on actually means that you should increase the brightness of those lights by 1.

The phrase turn off actually means that you should decrease the brightness of those lights by 1, to a minimum of zero.

The phrase toggle actually means that you should increase the brightness of those lights by 2.

What is the total brightness of all lights combined after following Santa's instructions?

For example:

turn on 0,0 through 0,0 would increase the total brightness by 1.
toggle 0,0 through 999,999 would increase the total brightness by 2000000.
*/
fun litLightsWithBrightness(instructions: List<String>, gridSize: Int): Int {

    val grid = LightGrid(gridSize)

    instructions.forEach {
        val instruction = parseInstruction(it)

        grid.setRegion(instruction.region) { level ->
            when (instruction.action) {
                Action.TURN_ON -> level + 1
                Action.TURN_OFF -> Math.max(0, level - 1)
                Action.TOGGLE -> level + 2
            }
        }
    }

    return grid.litCount()
}