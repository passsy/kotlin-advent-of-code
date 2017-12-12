package y2017

import com.pascalwelsch.aoc.challenge
import y2017.Day12_DigitalPlumber.part1
import y2017.Day12_DigitalPlumber.part2

fun main(args: Array<String>) {
    part1()
    part2()
}


object Day12_DigitalPlumber {

    val part1 = challenge("Day 12 - Part One") {
        inputFile("2017/12.txt")

        solveMultiLine {
            val dualCons = buildMap(it.map { parseRelation(it) })
            result = connectedPrograms(0, dualCons).count()
        }
    }

    fun connectedPrograms(of: Int, dualCons: HashMap<Int, Set<Int>>): List<Int> {
        val found = mutableListOf<Int>()

        fun find(name: Int): List<Int> {
            found += name
            val childs = dualCons[name] ?: emptyList<Int>()
            return (childs + childs.filter { it !in found }.flatMap { find(it) }).toSet().toList()
        }

        return find(of).sorted()
    }

    fun buildMap(connections: List<Pair<Int, List<Int>>>): HashMap<Int, Set<Int>> {
        val dualCons = hashMapOf<Int, Set<Int>>()

        connections.forEach { con ->
            dualCons[con.first] = dualCons.getOrDefault(con.first, setOf()) + con.second

            con.second.forEach { rev ->
                dualCons[rev] = dualCons.getOrDefault(rev, setOf()) + con.first
            }
        }
        return dualCons
    }


    fun parseRelation(input: String): Pair<Int, List<Int>> {
        val (_, name, connections) = "(\\d+) <-> (.*)".toRegex().find(input)!!.groupValues
        return name.toInt() to connections.split(", ").map { it.toInt() }
    }

    val part2 = challenge("Day 12 - Part One") {
        inputFile("2017/12.txt")

        solveMultiLine {
            val dualCons = buildMap(it.map { parseRelation(it) })

            val groups = mutableListOf<List<Int>>()
            dualCons.forEach { first, second ->
                if (groups.firstOrNull { first in it } == null) {
                    groups.add(connectedPrograms(first, dualCons))
                }
            }

            result = groups.size
        }
    }
}