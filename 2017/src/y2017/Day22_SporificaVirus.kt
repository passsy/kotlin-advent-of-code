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

    val part1 = challenge("Day 22 - Part One") {
        solve {
            result = moveSimple(10_000, inputFile("2017/22.txt").lines().map { it.toList() })
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

    val part2 = challenge("Day 22 - Part Two") {
        solve {
            result = moveAdvanced(10_000_000, inputFile("2017/22.txt").lines().map { it.toList() })
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