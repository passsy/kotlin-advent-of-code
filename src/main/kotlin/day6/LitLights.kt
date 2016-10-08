package day6

import common.solveFromInput


fun main(args: Array<String>) {

    solveFromInput("day6-1") { input, output ->
        val result = litLights(input, 1000)
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
        val regex = "(.*)\\s(\\d+),(\\d+)\\sthrough\\s(\\d+),(\\d+)".toRegex()
        val (all, command, fromX, fromY, toX, toY) = regex.find(it)!!.groupValues
        val region = GridRegion(fromX.toInt(), fromY.toInt(), toX.toInt(), toY.toInt())

        val action: (Boolean) -> Boolean = when (command) {
            "turn on" -> {
                { true }
            }
            "toggle" -> {
                { state -> !state }
            }
            "turn off" -> {
                { false }
            }
            else -> throw IllegalArgumentException("unknown command")
        }

        grid.setRegion(region, action)
    }

    return grid.litCount()
}

class LightGrid(val size: Int) {

    val grid = Array(size) { Array(size) { false } }

    fun setRegion(region: GridRegion, setter: (Boolean) -> Boolean) {
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
                if (grid[i][j]) count++
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
