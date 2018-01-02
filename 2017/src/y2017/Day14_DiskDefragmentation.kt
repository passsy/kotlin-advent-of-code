package y2017

import com.pascalwelsch.aoc.challenge
import com.pascalwelsch.aoc.toBits
import y2017.Day14_DiskDefragmentation.part1
import y2017.Day14_DiskDefragmentation.part2


fun main(args: Array<String>) {
    part1()
    part2()
}

object Day14_DiskDefragmentation {

    //--- Day 14: Disk Defragmentation ---
    //Suddenly, a scheduled job activates the system's disk defragmenter. Were the situation different, you might sit and watch it for a while, but today, you just don't have that kind of time. It's soaking up valuable system resources that are needed elsewhere, and so the only option is to help it finish its task as soon as possible.
    //
    //The disk in question consists of a 128x128 grid; each square of the grid is either free or used. On this disk, the state of the grid is tracked by the bits in a sequence of knot hashes.
    //
    //A total of 128 knot hashes are calculated, each corresponding to a single row in the grid; each hash contains 128 bits which correspond to individual grid squares. Each bit of a hash indicates whether that square is free (0) or used (1).
    //
    //The hash inputs are a key string (your puzzle input), a dash, and a number from 0 to 127 corresponding to the row. For example, if your key string were flqrgnkx, then the first row would be given by the bits of the knot hash of flqrgnkx-0, the second row from the bits of the knot hash of flqrgnkx-1, and so on until the last row, flqrgnkx-127.
    //
    //The output of a knot hash is traditionally represented by 32 hexadecimal digits; each of these digits correspond to 4 bits, for a total of 4 * 32 = 128 bits. To convert to bits, turn each hexadecimal digit to its equivalent binary value, high-bit first: 0 becomes 0000, 1 becomes 0001, e becomes 1110, f becomes 1111, and so on; a hash that begins with a0c2017... in hexadecimal would begin with 10100000110000100000000101110000... in binary.
    //
    //Continuing this process, the first 8 rows and columns for key flqrgnkx appear as follows, using # to denote used squares, and . to denote free ones:
    //
    //##.#.#..-->
    //.#.#.#.#
    //....#.#.
    //#.#.##.#
    //.##.#...
    //##..#..#
    //.#...#..
    //##.#.##.-->
    //|      |
    //V      V
    //In this example, 8108 squares are used across the entire 128x128 grid.
    //
    //Given your actual key string, how many squares are used?
    val part1 = challenge("Day 14 - Part One") {
        solve {
            result = buildGrid("amgozmfv").usedSquares() //8222
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
        solve {
            result = buildGrid("amgozmfv").regionCount() //1086
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