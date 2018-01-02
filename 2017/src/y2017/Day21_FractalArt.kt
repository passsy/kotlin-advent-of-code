package y2017

import com.pascalwelsch.aoc.challenge
import y2017.Day21_FractalArt.part1
import y2017.Day21_FractalArt.part2
import kotlin.math.sqrt

fun main(args: Array<String>) {
    part1()
    part2()
}

typealias Art = List<List<Char>>

object Day21_FractalArt {
    //--- Day 21: Fractal Art ---
    //You find a program trying to generate some art. It uses a strange process that involves repeatedly enhancing the detail of an image through a set of rules.
    //
    //The image consists of a two-dimensional square grid of pixels that are either on (#) or off (.). The program always begins with this pattern:
    //
    //.#.
    //..#
    //###
    //Because the pattern is both 3 pixels wide and 3 pixels tall, it is said to have a size of 3.
    //
    //Then, the program repeats the following process:
    //
    //If the size is evenly divisible by 2, break the pixels up into 2x2 squares, and convert each 2x2 square into a 3x3 square by following the corresponding enhancement rule.
    //Otherwise, the size is evenly divisible by 3; break the pixels up into 3x3 squares, and convert each 3x3 square into a 4x4 square by following the corresponding enhancement rule.
    //Because each square of pixels is replaced by a larger one, the image gains pixels and so its size increases.
    //
    //The artist's book of enhancement rules is nearby (your puzzle input); however, it seems to be missing rules. The artist explains that sometimes, one must rotate or flip the input pattern to find a match. (Never rotate or flip the output pattern, though.) Each pattern is written concisely: rows are listed as single units, ordered top-down, and separated by slashes. For example, the following rules correspond to the adjacent patterns:
    //
    //../.#  =  ..
    //.#
    //
    //.#.
    //.#./..#/###  =  ..#
    //###
    //
    //#..#
    //#..#/..../#..#/.##.  =  ....
    //#..#
    //.##.
    //When searching for a rule to use, rotate and flip the pattern as necessary. For example, all of the following patterns match the same rule:
    //
    //.#.   .#.   #..   ###
    //..#   #..   #.#   ..#
    //###   ###   ##.   .#.
    //Suppose the book contained the following two rules:
    //
    //../.# => ##./#../...
    //.#./..#/### => #..#/..../..../#..#
    //As before, the program begins with this pattern:
    //
    //.#.
    //..#
    //###
    //The size of the grid (3) is not divisible by 2, but it is divisible by 3. It divides evenly into a single square; the square matches the second rule, which produces:
    //
    //#..#
    //....
    //....
    //#..#
    //The size of this enhanced grid (4) is evenly divisible by 2, so that rule is used. It divides evenly into four squares:
    //
    //#.|.#
    //..|..
    //--+--
    //..|..
    //#.|.#
    //Each of these squares matches the same rule (../.# => ##./#../...), three of which require some flipping and rotation to line up with the rule. The output for the rule is the same in all four cases:
    //
    //##.|##.
    //#..|#..
    //...|...
    //---+---
    //##.|##.
    //#..|#..
    //...|...
    //Finally, the squares are joined into a new grid:
    //
    //##.##.
    //#..#..
    //......
    //##.##.
    //#..#..
    //......
    //Thus, after 2 iterations, the grid contains 12 pixels that are on.
    //
    //How many pixels stay on after 5 iterations?
    val part1 = challenge("Day 21 - Part 1") {

        solve {
            val patterns = inputFile("2017/21.txt").lines().map { parseRule(it) }

            var art: Art = listOf(
                    listOf('.', '#', '.'),
                    listOf('.', '.', '#'),
                    listOf('#', '#', '#'))

            repeat(5) {
                art = art.zoom(patterns)
            }

            result = art.flatten().count { it == '#' } //110
        }
    }

    fun Art.zoom(patterns: List<Pattern>): Art {

        if (size == 2) {
            patterns.filter { it.from.size == 2 }.forEach { pattern ->
                if (pattern.matches(this)) {
                    return pattern.enhanced
                }
            }
        }
        if (size == 3) {
            patterns.filter { it.from.size == 3 }.forEach { pattern ->
                if (pattern.matches(this)) {
                    return pattern.enhanced
                }
            }
        }

        return this.tile().map { it.zoom(patterns) }.combine()
    }

    fun String.toArt() = split('/').map { it.toList() }
    fun parseRule(input: String): Pattern {
        val (_, from, to) = "(.*) => (.*)".toRegex().matchEntire(input)!!.groupValues
        return Pattern(from.toArt(), to.toArt())
    }

    class Pattern(val from: Art, val enhanced: Art) {
        private val o = from
        private val r = from.rotate90()
        private val rr = from.rotate90().rotate90()
        private val rrr = from.rotate90().rotate90().rotate90()

        private val of = from.flip()
        private val rf = from.rotate90().flip()
        private val rrf = from.rotate90().rotate90().flip()
        private val rrrf = from.rotate90().rotate90().rotate90().flip()
        val variants = listOf(o, r, rr, rrr, of, rf, rrf, rrrf)
        fun matches(art: Art): Boolean {
            return variants.contains(art)
        }
    }

    fun Art.flip(): Art = map { it.reversed() }

    fun Art.rotate90(): Art {
        val r90 = mutableListOf<MutableList<Char>>().also { list ->
            (1..size).forEach { list.add(mutableListOf()) }
        }

        reversed().forEach { list ->
            list.forEachIndexed { index, c ->
                r90[index].add(c)
            }
        }
        return r90
    }

    fun Art.tile(): List<Art> {
        val divisableBy2 = size % 2 == 0
        val divisableBy3 = size % 3 == 0
        require(divisableBy2 || divisableBy3) { "size must be dividable by 2 or 3, is $size" }
        require(size >= 4) { "size must be at least 4 to tile, is $size" }

        if (divisableBy2) {

            val all = mutableListOf<Art>()
            for (i in 0 until size step 2) {
                for (j in 0 until size step 2) {
                    val tile = listOf(
                            listOf(this[i + 0][j], this[i + 0][j + 1]),
                            listOf(this[i + 1][j], this[i + 1][j + 1])
                    )
                    all.add(tile)
                }
            }
            return all
        }
        if (divisableBy3) {
            val all = mutableListOf<Art>()
            for (i in 0 until size step 3) {
                for (j in 0 until size step 3) {
                    val tile = listOf(
                            listOf(this[i + 0][j], this[i + 0][j + 1], this[i + 0][j + 2]),
                            listOf(this[i + 1][j], this[i + 1][j + 1], this[i + 1][j + 2]),
                            listOf(this[i + 2][j], this[i + 2][j + 1], this[i + 2][j + 2])
                    )
                    all.add(tile)
                }
            }
            return all
        }
        throw(IllegalStateException("huh?"))
    }

    fun List<Art>.combine(): Art {
        val sideLength = sqrt(size.toDouble()).toInt()
        return chunked(sideLength)
                .map { artLine ->
                    (0 until artLine[0].size)
                            .map { artLine.map { tile -> tile[it] }.flatten() }
                }
                .flatten()
    }

    // How many pixels stay on after 18 iterations?
    val part2 = challenge("Day 21 - Part 2") {
        solve {
            val patterns = inputFile("2017/21.txt").lines().map { parseRule(it) }

            var art: Art = listOf(
                    listOf('.', '#', '.'),
                    listOf('.', '.', '#'),
                    listOf('#', '#', '#'))

            repeat(18) {
                art = art.zoom(patterns)
            }

            result = art.flatten().count { it == '#' } //1277716
        }
    }

}