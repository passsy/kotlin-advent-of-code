package y2017

import com.pascalwelsch.aoc.challenge
import java.util.*

fun main(args: Array<String>) {
    Day16_PermutationPromenade.part1()
    Day16_PermutationPromenade.part2()
}

object Day16_PermutationPromenade {

    //--- Day 16: Permutation Promenade ---
    //You come upon a very unusual sight; a group of programs here appear to be dancing.
    //
    //There are sixteen programs in total, named a through p. They start by standing in a line: a stands in position 0, b stands in position 1, and so on until p, which stands in position 15.
    //
    //The programs' dance consists of a sequence of dance moves:
    //
    //Spin, written sX, makes X programs move from the end to the front, but maintain their order otherwise. (For example, s3 on abcde produces cdeab).
    //Exchange, written xA/B, makes the programs at positions A and B swap places.
    //Partner, written pA/B, makes the programs named A and B swap places.
    //For example, with only five programs standing in a line (abcde), they could do the following dance:
    //
    //s1, a spin of size 1: eabcd.
    //x3/4, swapping the last two programs: eabdc.
    //pe/b, swapping programs e and b: baedc.
    //After finishing their dance, the programs end up in order baedc.
    //
    //You watch the dance for a while and record their dance moves (your puzzle input). In what order are the programs standing after their dance?
    val part1 = challenge("Day 16 - Part One") {
        solve {
            val commands = inputFile("2017/16.txt").split(",").map { parseCommand(it) }
            result = dance(commands).joinToString("") //ceijbfoamgkdnlph
        }
    }

    sealed class Command {
        class Spin(val count: Int) : Command()
        class Exchange(val a: Int, val b: Int) : Command()
        class Partner(val a: String, val b: String) : Command()
    }

    fun parseCommand(input: String): Command = when {
        input.startsWith("s") -> {
            // Spin, move tail to head
            val spinCount = "s(\\d+)".toRegex().find(input)!!.groupValues[1].toInt()
            Command.Spin(spinCount)
        }
        input.startsWith("x") -> {
            // Exchange, swap by index
            val (_, swapA, swapB) = "x(\\d+)/(\\d+)".toRegex().find(input)!!.groupValues
            Command.Exchange(swapA.toInt(), swapB.toInt())
        }
        input.startsWith("p") -> {
            // Partner, swap by name
            val (_, itemA, itemB) = "p(\\w+)/(\\w+)".toRegex().find(input)!!.groupValues
            Command.Partner(itemA, itemB)
        }
        else -> throw IllegalArgumentException("unknown command")
    }


    fun dance(commands: List<Command>, rounds: Int = 1, initialOrder: List<String> = initialProgramOrder): List<String> {
        val steps = Stack<List<String>>()

        var items = initialOrder.toMutableList()
        repeat(rounds) {
            if (items in steps) {
                // detected cycle
                return steps[rounds % steps.size]
            } else {
                steps += items.toList()

                commands.forEach {
                    when (it) {
                        is Command.Spin -> {
                            // Spin, move tail to head
                            val end = items.subList(items.size - it.count, items.size)
                            items = (end + items.dropLast(it.count)).toMutableList()
                        }
                        is Command.Exchange -> {
                            // Exchange, swap by index
                            val a = items[it.a]
                            val b = items[it.b]
                            items[it.a] = b
                            items[it.b] = a
                        }
                        is Command.Partner -> {
                            // Partner, swap by name
                            val swapA = items.indexOf(it.a)
                            val swapB = items.indexOf(it.b)
                            items[swapA] = it.b
                            items[swapB] = it.a
                        }
                    }
                }
            }
        }

        return items.toList()
    }

    private val initialProgramOrder = listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p")

    //--- Part Two ---
    //Now that you're starting to get a feel for the dance moves, you turn your attention to the dance as a whole.
    //
    //Keeping the positions they ended up in from their previous dance, the programs perform it again and again: including the first dance, a total of one billion (1000000000) times.
    //
    //In the example above, their second dance would begin with the order baedc, and use the same dance moves:
    //
    //s1, a spin of size 1: cbaed.
    //x3/4, swapping the last two programs: cbade.
    //pe/b, swapping programs e and b: ceadb.
    //In what order are the programs standing after their billion dances?
    val part2 = challenge("Day 16 - Part Two") {
        solve {
            val commands = inputFile("2017/16.txt").split(",").map { parseCommand(it) }
            result = dance(commands, 1_000_000).joinToString("") //pnhajoekigcbflmd
        }
    }
}