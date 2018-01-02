package y2017

import com.pascalwelsch.aoc.challenge
import y2017.Day22_SporificaVirus.NodeState.*
import y2017.Day22_SporificaVirus.Orientation.*
import y2017.Day22_SporificaVirus.part1
import y2017.Day22_SporificaVirus.part2


fun main(args: Array<String>) {
    part1()
    part2()
}

object Day22_SporificaVirus {

    //--- Day 22: Sporifica Virus ---
    //Diagnostics indicate that the local grid computing cluster has been contaminated with the Sporifica Virus. The grid computing cluster is a seemingly-infinite two-dimensional grid of compute nodes. Each node is either clean or infected by the virus.
    //
    //To prevent overloading the nodes (which would render them useless to the virus) or detection by system administrators, exactly one virus carrier moves through the network, infecting or cleaning nodes as it moves. The virus carrier is always located on a single node in the network (the current node) and keeps track of the direction it is facing.
    //
    //To avoid detection, the virus carrier works in bursts; in each burst, it wakes up, does some work, and goes back to sleep. The following steps are all executed in order one time each burst:
    //
    //If the current node is infected, it turns to its right. Otherwise, it turns to its left. (Turning is done in-place; the current node does not change.)
    //If the current node is clean, it becomes infected. Otherwise, it becomes cleaned. (This is done after the node is considered for the purposes of changing direction.)
    //The virus carrier moves forward one node in the direction it is facing.
    //Diagnostics have also provided a map of the node infection status (your puzzle input). Clean nodes are shown as .; infected nodes are shown as #. This map only shows the center of the grid; there are many more nodes beyond those shown, but none of them are currently infected.
    //
    //The virus carrier begins in the middle of the map facing up.
    //
    //For example, suppose you are given a map like this:
    //
    //..#
    //#..
    //...
    //Then, the middle of the infinite grid looks like this, with the virus carrier's position marked with [ ]:
    //
    //. . . . . . . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //. . . . . # . . .
    //. . . #[.]. . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //The virus carrier is on a clean node, so it turns left, infects the node, and moves left:
    //
    //. . . . . . . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //. . . . . # . . .
    //. . .[#]# . . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //The virus carrier is on an infected node, so it turns right, cleans the node, and moves up:
    //
    //. . . . . . . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //. . .[.]. # . . .
    //. . . . # . . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //Four times in a row, the virus carrier finds a clean, infects it, turns left, and moves forward, ending in the same place and still facing up:
    //
    //. . . . . . . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //. . #[#]. # . . .
    //. . # # # . . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //Now on the same node as before, it sees an infection, which causes it to turn right, clean the node, and move forward:
    //
    //. . . . . . . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //. . # .[.]# . . .
    //. . # # # . . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //After the above actions, a total of 7 bursts of activity had taken place. Of them, 5 bursts of activity caused an infection.
    //
    //After a total of 70, the grid looks like this, with the virus carrier facing up:
    //
    //. . . . . # # . .
    //. . . . # . . # .
    //. . . # . . . . #
    //. . # . #[.]. . #
    //. . # . # . . # .
    //. . . . . # # . .
    //. . . . . . . . .
    //. . . . . . . . .
    //By this time, 41 bursts of activity caused an infection (though most of those nodes have since been cleaned).
    //
    //After a total of 10000 bursts of activity, 5587 bursts will have caused an infection.
    //
    //Given your actual map, after 10000 bursts of activity, how many bursts cause a node to become infected? (Do not count nodes that begin infected.)
    val part1 = challenge("Day 22 - Part One") {
        solve {
            result = moveSimple(10_000, inputFile("2017/22.txt").lines().map { it.toList() }) //5246
        }
    }

    fun moveSimple(rounds: Int, map: List<List<Char>>): Int {
        var infectionCount = 0
        move(rounds, map,
                mapState = { state ->
                    if (state == CLEAN) {
                        infectionCount++
                        INFECTED
                    } else {
                        CLEAN
                    }
                },
                turn = { state, orientation ->
                    if (state == CLEAN) {
                        orientation.rotate(Rotation.LEFT)
                    } else {
                        orientation.rotate(Rotation.RIGHT)
                    }
                })
        return infectionCount
    }

    fun move(rounds: Int, map: List<List<Char>>,
             mapState: (NodeState) -> NodeState,
             turn: (NodeState, Orientation) -> Orientation) {
        // CARE reversed, y:x instead of normal x:y
        val nodes = hashMapOf<Int, HashMap<Int, NodeState>>()

        val offsetY = map.size / 2
        val offsetX = map[0].size / 2

        map.forEachIndexed { rowIndex, row ->
            nodes.put(rowIndex - offsetY, HashMap())
            row.forEachIndexed { colIndex, c ->
                val state = if (c == '.') CLEAN else INFECTED
                nodes[rowIndex - offsetY]!![colIndex - offsetX] = state
            }
        }

        var x = 0
        var y = 0
        var orientation = NORTH

        fun current(): NodeState {
            return nodes[y]?.get(x) ?: CLEAN
        }

        fun set(state: NodeState) {
            var row = nodes[y]
            if (row == null) {
                nodes[y] = HashMap()
                row = nodes[y]
            }
            row!![x] = state
        }

        fun printNodes() {
            val minY = nodes.keys.min()!!
            val maxY = nodes.keys.max()!!
            val minX = nodes.values.flatMap { it.keys }.min()!!
            val maxX = nodes.values.flatMap { it.keys }.max()!!

            println("x=$x y=$y")
            for (i in minY..maxY) {
                for (j in minX..maxX) {
                    val state: NodeState = nodes[i]?.get(j) ?: CLEAN
                    print(when (state) {
                        CLEAN -> "."
                        INFECTED -> "#"
                        WEAKENED -> "W"
                        FLAGGED -> "F"
                    })
                }
                println()
            }
            println()
        }

        repeat(rounds) {
            val state = current()
            //printNodes()

            val newState = mapState(state)
            set(newState)

            // val rotation =
            //        orientation.rotate(rotation)
            orientation = turn(state, orientation)

            when (orientation) {
                NORTH -> y--
                EAST -> x++
                SOUTH -> y++
                WEST -> x--
            }
        }
    }

    enum class NodeState { CLEAN, WEAKENED, INFECTED, FLAGGED }
    enum class Rotation { LEFT, RIGHT }
    enum class Orientation { NORTH, EAST, SOUTH, WEST;

        fun rotate(rotation: Rotation): Orientation {
            return when (rotation) {
                Rotation.LEFT -> when (this) {
                    NORTH -> WEST
                    EAST -> NORTH
                    SOUTH -> EAST
                    WEST -> SOUTH
                }
                Rotation.RIGHT -> when (this) {
                    NORTH -> EAST
                    EAST -> SOUTH
                    SOUTH -> WEST
                    WEST -> NORTH
                }
            }
        }
    }

    //--- Part Two ---
    //As you go to remove the virus from the infected nodes, it evolves to resist your attempt.
    //
    //Now, before it infects a clean node, it will weaken it to disable your defenses. If it encounters an infected node, it will instead flag the node to be cleaned in the future. So:
    //
    //Clean nodes become weakened.
    //Weakened nodes become infected.
    //Infected nodes become flagged.
    //Flagged nodes become clean.
    //Every node is always in exactly one of the above states.
    //
    //The virus carrier still functions in a similar way, but now uses the following logic during its bursts of action:
    //
    //Decide which way to turn based on the current node:
    //If it is clean, it turns left.
    //If it is weakened, it does not turn, and will continue moving in the same direction.
    //If it is infected, it turns right.
    //If it is flagged, it reverses direction, and will go back the way it came.
    //Modify the state of the current node, as described above.
    //The virus carrier moves forward one node in the direction it is facing.
    //Start with the same map (still using . for clean and # for infected) and still with the virus carrier starting in the middle and facing up.
    //
    //Using the same initial state as the previous example, and drawing weakened as W and flagged as F, the middle of the infinite grid looks like this, with the virus carrier's position again marked with [ ]:
    //
    //. . . . . . . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //. . . . . # . . .
    //. . . #[.]. . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //This is the same as before, since no initial nodes are weakened or flagged. The virus carrier is on a clean node, so it still turns left, instead weakens the node, and moves left:
    //
    //. . . . . . . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //. . . . . # . . .
    //. . .[#]W . . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //The virus carrier is on an infected node, so it still turns right, instead flags the node, and moves up:
    //
    //. . . . . . . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //. . .[.]. # . . .
    //. . . F W . . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //This process repeats three more times, ending on the previously-flagged node and facing right:
    //
    //. . . . . . . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //. . W W . # . . .
    //. . W[F]W . . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //Finding a flagged node, it reverses direction and cleans the node:
    //
    //. . . . . . . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //. . W W . # . . .
    //. .[W]. W . . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //The weakened node becomes infected, and it continues in the same direction:
    //
    //. . . . . . . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //. . W W . # . . .
    //.[.]# . W . . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //. . . . . . . . .
    //Of the first 100 bursts, 26 will result in infection. Unfortunately, another feature of this evolved virus is speed; of the first 10000000 bursts, 2511944 will result in infection.
    //
    //Given your actual map, after 10000000 bursts of activity, how many bursts cause a node to become infected? (Do not count nodes that begin infected.)
    val part2 = challenge("Day 22 - Part Two") {
        solve {
            result = moveAdvanced(10_000_000, inputFile("2017/22.txt").lines().map { it.toList() }) //2512059
        }
    }

    fun moveAdvanced(rounds: Int, map: List<List<Char>>): Int {
        var infectionCount = 0
        move(rounds, map,
                mapState = { state ->
                    when (state) {
                        CLEAN -> WEAKENED
                        WEAKENED -> {
                            infectionCount++
                            INFECTED
                        }
                        INFECTED -> FLAGGED
                        FLAGGED -> CLEAN
                    }
                },
                turn = { state, orientation ->
                    when (state) {
                        CLEAN -> orientation.rotate(Rotation.LEFT)
                        WEAKENED -> orientation
                        INFECTED -> orientation.rotate(Rotation.RIGHT)
                        FLAGGED -> orientation.rotate(Rotation.RIGHT).rotate(Rotation.RIGHT) // reverse
                    }
                })
        return infectionCount
    }
}