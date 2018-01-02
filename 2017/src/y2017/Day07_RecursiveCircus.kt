package y2017

import com.pascalwelsch.aoc.challenge
import y2017.Day07_RecursiveCircus.part1
import y2017.Day07_RecursiveCircus.part2

fun main(args: Array<String>) {
    part1()
    part2()
}

object Day07_RecursiveCircus {
    //--- Day 7: Recursive Circus ---
    //
    //Wandering further through the circuits of the computer, you come upon a tower of programs that have gotten themselves into a bit of trouble. A recursive algorithm has gotten out of hand, and now they're balanced precariously in a large tower.
    //
    //One program at the bottom supports the entire tower. It's holding a large disc, and on the disc are balanced several more sub-towers. At the bottom of these sub-towers, standing on the bottom disc, are other programs, each holding their own disc, and so on. At the very tops of these sub-sub-sub-...-towers, many programs stand simply keeping the disc below them balanced but with no disc of their own.
    //
    //You offer to help, but first you need to understand the structure of these towers. You ask each program to yell out their name, their weight, and (if they're holding a disc) the names of the programs immediately above them balancing on that disc. You write this information down (your puzzle input). Unfortunately, in their panic, they don't do this in an orderly fashion; by the time you're done, you're not sure which program gave which information.
    //
    //For example, if your list is the following:
    //
    //pbga (66)
    //xhth (57)
    //ebii (61)
    //havc (66)
    //ktlj (57)
    //fwft (72) -> ktlj, cntj, xhth
    //qoyq (66)
    //padx (45) -> pbga, havc, qoyq
    //tknk (41) -> ugml, padx, fwft
    //jptl (61)
    //ugml (68) -> gyxo, ebii, jptl
    //gyxo (61)
    //cntj (57)
    //...then you would be able to recreate the structure of the towers that looks like this:
    //
    //          gyxo
    //       /
    //ugml - ebii
    ///      \
    //|         jptl
    //|
    //|         pbga
    ///        /
    //tknk --- padx - havc
    //\        \
    //|         qoyq
    //|
    //|         ktlj
    //\      /
    //fwft - cntj
    //       \
    //          xhth
    //In this example, tknk is at the bottom of the tower (the bottom program), and is holding up ugml, padx, and fwft. Those programs are, in turn, holding up other programs; in this example, none of those programs are holding up any other programs, and are all the tops of their own towers. (The actual tower balancing in front of you is much larger.)
    //
    //Before you're ready to help them, you need to make sure your information is correct. What is the name of the bottom program?
    val part1 = challenge("Day 7 - Part One") {
        solve {
            val infos = inputFile("2017/7.txt").lines().map { parseInfo(it) }
            result = findRootNode(infos).name //17537
        }
    }

    fun findRootNode(infos: List<Node>): Node {
        val start = infos.first { it.childNames.isNotEmpty() }

        var node: Node = start
        while (true) {
            val parent = infos.firstOrNull { it.childNames.isNotEmpty() && node.name in it.childNames }
            if (parent == null) return node
            node = parent
        }
    }

    fun parseInfo(input: String): Node {
        val groups = "(\\w*) \\((\\d*)\\)(?>\\s->\\s(.+))*".toRegex()
                .find(input)?.groupValues ?: throw IllegalArgumentException("doesn't match")

        val childs = groups.getOrNull(3)
        val childList = if (childs.isNullOrBlank()) {
            emptyList()
        } else {
            childs?.split(", ")?.toList() ?: emptyList()
        }
        return Node(groups[1], groups[2].toInt(), childList)
    }

    data class Node(
            val name: String,
            val weight: Int,
            val childNames: List<String> = emptyList()
    )


    //--- Part Two ---
    //
    //The programs explain the situation: they can't get down. Rather, they could get down, if they weren't expending all of their energy trying to keep the tower balanced. Apparently, one program has the wrong weight, and until it's fixed, they're stuck here.
    //
    //For any program holding a disc, each program standing on that disc forms a sub-tower. Each of those sub-towers are supposed to be the same weight, or the disc itself isn't balanced. The weight of a tower is the sum of the weights of the programs in that tower.
    //
    //In the example above, this means that for ugml's disc to be balanced, gyxo, ebii, and jptl must all have the same weight, and they do: 61.
    //
    //However, for tknk to be balanced, each of the programs standing on its disc and all programs above it must each match. This means that the following sums must all be the same:
    //
    //ugml + (gyxo + ebii + jptl) = 68 + (61 + 61 + 61) = 251
    //padx + (pbga + havc + qoyq) = 45 + (66 + 66 + 66) = 243
    //fwft + (ktlj + cntj + xhth) = 72 + (57 + 57 + 57) = 243
    //As you can see, tknk's disc is unbalanced: ugml's stack is heavier than the other two. Even though the nodes above ugml are balanced, ugml itself is too heavy: it needs to be 8 units lighter for its stack to weigh 243 and keep the towers balanced. If this change were made, its weight would be 60.
    //
    //Given that exactly one program is the wrong weight, what would its weight need to be to balance the entire tower?
    val part2 = challenge("Day 7 - Part Two") {
        solve {
            val infos = inputFile("2017/7.txt").lines().map { parseInfo(it) }
            result = correctErrorForBalancedTree(infos) ?: throw Exception("tree is already balanced") //7539
        }
    }


    fun correctErrorForBalancedTree(infos: List<Node>, root: Node = findRootNode(infos)): Int? {
        fun Node.children(): List<Node> = childNames.mapNotNull { childName -> infos.firstOrNull { it != this && it.name == childName } }

        fun Node.combinedWeight(): Int {
            val c = children()
            val childSum = c.map { it.combinedWeight() }.sum()
            return childSum + weight
        }

        val children = root.children()
        if (children.isEmpty()) {
            return null
        }

        val childrenByWeight = children.groupBy { it.combinedWeight() }
        if (childrenByWeight.size > 1) {
            // has multiple groups, expect only one to be off
            val theOneOff = childrenByWeight.entries.find { it.value.size == 1 }!!
            val otherWeight = childrenByWeight.entries.first { it != theOneOff }.key

            val weightDiff = theOneOff.key - otherWeight
            val offNode = theOneOff.value.first()

            val childDiff = correctErrorForBalancedTree(infos, offNode)
            if (childDiff == null) {
                // FOUND!
                // theOneOff should have a different weight
                return offNode.weight - weightDiff
            } else {
                // return result of recursions
                return childDiff
            }
        } else {
            // all weights are the same
            return null
        }
    }

}