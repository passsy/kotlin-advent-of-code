package y2017

import com.pascalwelsch.aoc.challenge
import com.pascalwelsch.aoc.toBits
import y2017.Day14_DiskDefragmentation.part1


fun main(args: Array<String>) {
    part1()
}

object Day14_DiskDefragmentation {

    val part1 = challenge("Day 14 - Part One") {
        inputText("amgozmfv")
        solve {
            result = buildGrid(it).usedSquares()
        }
    }

    fun List<List<Int>>.usedSquares(): Int = flatMap { it }.filter { it == 1 }.sum()

    fun buildGrid(input: String): List<List<Int>> {
        return (0..127).map { "$input-$it" }
                .map { Day10_KnotHash.knotHash(it) }
                .map { it.toBits() }
                .map { it.toList().map { it.toString().toInt() } }
                .toList()
    }

    //--- Part Two ---
    //
    //Now, all the defragmenter needs to know is the number of regions. A region is a group of used squares that are all adjacent, not including diagonals. Every used square is in exactly one region: lone used squares form their own isolated regions, while several adjacent squares all count as a single region.
    //
    //In the example above, the following nine regions are visible, each marked with a distinct digit:
    //
    //11.2.3..-->
    //.1.2.3.4
    //....5.6.
    //7.8.55.9
    //.88.5...
    //88..5..8
    //.8...8..
    //88.8.88.-->
    //|      |
    //V      V
    //Of particular interest is the region marked 8; while it does not appear contiguous in this small view, all of the squares marked 8 are connected when considering the whole 128x128 grid. In total, in this example, 1242 regions are present.
    //
    //How many regions are present given your key string?
    val part2 = challenge("Day 14 - Part Two") {
        inputText("amgozmfv")
        solve {
            result = buildGrid(it).regionCount()
        }
    }


    fun List<List<Int>>.regionCount(): Int {
        val groups = mutableMapOf<Int, List<Pair<Int, Int>>>()

        var groupId = 0

        forEachIndexed row@ { i, list ->
            list.forEachIndexed col@ { j, value ->
                if (value != 1) return@col

                // already part of a group
                val cell = i to j
                val groupedItems = groups.values.flatMap { it }
                if (cell in groupedItems) return@col

                val items = mutableListOf<Pair<Int, Int>>()
                items.add(cell) // next ungrouped item


                fun Pair<Int, Int>.addAdjacentCells() {

                    val top = first to second + 1
                    val right = first + 1 to second
                    val bottom = first to second - 1
                    val left = first - 1 to second


                    if (second + 1 < 128 && get(first).get(second + 1) == 1
                            && top !in items && top !in groupedItems) {
                        items += top
                        top.addAdjacentCells()
                    }
                    if (first + 1 < 128 && get(first + 1).get(second) == 1
                            && right !in items && right !in groupedItems) {
                        items += right
                        right.addAdjacentCells()
                    }
                    if (second - 1 >= 0 && get(first).get(second - 1) == 1
                            && bottom !in items && bottom !in groupedItems) {
                        items += bottom
                        bottom.addAdjacentCells()
                    }
                    if (first - 1 >= 0 && get(first - 1).get(second) == 1
                            && left !in items && left !in groupedItems) {
                        items += left
                        left.addAdjacentCells()
                    }
                }

                // add adjacent items
                cell.addAdjacentCells()

                groups.put(groupId, items)
                groupId++
            }
        }

        return groups.keys.count()
    }

}