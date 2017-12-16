package y2017

import com.pascalwelsch.aoc.challenge
import java.util.*

fun main(args: Array<String>) {
    Day16_PermutationPromenade.part1()
    Day16_PermutationPromenade.part2()
}

object Day16_PermutationPromenade {
    val part1 = challenge("Day 16 - Part One") {
        inputFile("2017/16.txt")
        solve {
            val commands = it.split(",").map { parseCommand(it) }
            result = dance(commands).joinToString("")
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


    fun dance(commands: List<Command>,
              rounds: Int = 1,
              initialOrder: List<String> = initialProgramOrder): List<String> {
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

    private val initialProgramOrder =
            listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p")

    val part2 = challenge("Day 16 - Part Two") {
        inputFile("2017/16.txt")
        solve {
            val commands = it.split(",").map { parseCommand(it) }
            result = dance(commands, 1_000_000).joinToString("")
        }
    }
}