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

    val part1 = challenge("Day 21 - Part 1") {
        inputFile("2017/21.txt")
        solveMultiLine {
            val patterns = it.map { parseRule(it) }

            var art: Art = listOf(
                    listOf('.', '#', '.'),
                    listOf('.', '.', '#'),
                    listOf('#', '#', '#'))

            repeat(5) {
                art = art.zoom(patterns)
            }

            result = art.flatten().count { it == '#' }

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


    val part2 = challenge("Day 21 - Part 2") {
        inputFile("2017/21.txt")
        solveMultiLine {
            val patterns = it.map { parseRule(it) }

            var art: Art = listOf(
                    listOf('.', '#', '.'),
                    listOf('.', '.', '#'),
                    listOf('#', '#', '#'))

            repeat(18) {
                art = art.zoom(patterns)
            }

            result = art.flatten().count { it == '#' }

        }
    }

}