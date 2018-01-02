package y2017

import com.pascalwelsch.aoc.challenge

fun main(args: Array<String>) {
    Day03_SpiralMemory.part1()
    Day03_SpiralMemory.part2()
}

object Day03_SpiralMemory {

    //--- Day 3: Spiral Memory ---
    //
    //You come across an experimental new kind of memory stored on an infinite two-dimensional grid.
    //
    //Each square on the grid is allocated in a spiral pattern starting at a location marked 1 and then counting up while spiraling outward. For example, the first few squares are allocated like this:
    //
    //17  16  15  14  13
    //18   5   4   3  12
    //19   6   1   2  11
    //20   7   8   9  10
    //21  22  23---> ...
    //While this is very space-efficient (no squares are skipped), requested data must be carried back to square 1 (the location of the only access port for this memory system) by programs that can only move up, down, left, or right. They always take the shortest path: the Manhattan Distance between the location of the data and square 1.
    //
    //For example:
    //
    //Data from square 1 is carried 0 steps, since it's at the access port.
    //Data from square 12 is carried 3 steps, such as: down, left, left.
    //Data from square 23 is carried only 2 steps: up twice.
    //Data from square 1024 must be carried 31 steps.
    //How many steps are required to carry the data from the square identified in your puzzle input all the way to the access port?
    val part1 = challenge("Day 3 - Part One") {
        solve {
            result = distance(277678) //475
        }
    }

    fun distance(destination: Int): Int {
        if (destination == 1) return 0

        var ring = 0
        var minRingValue = 1
        var i = 1
        while (destination > i) {
            ring++
            minRingValue = i + 1
            i = Math.pow((2.0 * ring + 1), 2.0).toInt()
        }

        val sideLength = ring + ring
        val distance = destination - minRingValue
        val startOffset = Math.min(0, -ring + 1)

        val steps = Math.abs((distance % sideLength) + startOffset)

        return ring + steps
    }


    //--- Part Two ---
    //
    //As a stress test on the system, the programs here clear the grid and then store the value 1 in square 1. Then, in the same allocation order as shown above, they store the sum of the values in all adjacent squares, including diagonals.
    //
    //So, the first few squares' values are chosen as follows:
    //
    //Square 1 starts with the value 1.
    //Square 2 has only one adjacent filled square (with value 1), so it also stores 1.
    //Square 3 has both of the above squares as neighbors and stores the sum of their values, 2.
    //Square 4 has all three of the aforementioned squares as neighbors and stores the sum of their values, 4.
    //Square 5 only has the first and fourth squares as neighbors, so it gets the value 5.
    //Once a square is written, its value does not change. Therefore, the first few squares would receive the following values:
    //
    //147  142  133  122   59
    //304    5    4    2   57
    //330   10    1    1   54
    //351   11   23   25   26
    //362  747  806--->   ...
    //What is the first value written that is larger than your puzzle input?
    val part2 = challenge("Day 3 - Part Two") {
        solve {
            result = firstLargerNumber(277678) //279138
        }
    }

    fun firstLargerNumber(limit: Int): Int {
        val array = hashMapOf<Pair<Int, Int>, Int>()
        array.put(0 to 0, 1)
        var x = 0
        var y = 0

        fun right(): Int = array[x + 1 to y] ?: 0
        fun left(): Int = array[x - 1 to y] ?: 0
        fun top(): Int = array[x to y + 1] ?: 0
        fun bottom(): Int = array[x to y - 1] ?: 0
        fun bottomLeft(): Int = array[x - 1 to y - 1] ?: 0
        fun bottomRight(): Int = array[x + 1 to y - 1] ?: 0
        fun topLeft(): Int = array[x - 1 to y + 1] ?: 0
        fun topRight(): Int = array[x + 1 to y + 1] ?: 0
        fun adjacentSum() = right() + topRight() + top() + topLeft() + left() + bottomLeft() + bottom() + bottomRight()

        while (true) {
            run move@ {
                if (left() + right() + bottom() + top() == 0) {
                    x++
                    return@move
                }

                if (right() + bottom() + top() == 0) {
                    y++
                    return@move
                }
                if (right() + top() + left() == 0) {
                    x--
                    return@move
                }
                if (left() + top() + bottom() == 0) {
                    y--
                    return@move
                }
                if (left() + right() + bottom() == 0) {
                    x++
                    return@move
                }
                if (left() + top() == 0) {
                    x--
                    return@move
                }
                if (bottom() + left() == 0) {
                    y--
                    return@move
                }
                if (right() + bottom() == 0) {
                    x++
                    return@move
                }
                if (top() + right() == 0) {
                    y++
                    return@move
                }
            }

            val newNumber = adjacentSum()
            if (newNumber > limit) return newNumber
            array[x to y] = newNumber
        }
    }
}