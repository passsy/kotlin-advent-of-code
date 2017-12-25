package y2017

import com.pascalwelsch.aoc.challenge
import y2017.Day25_HaltingProblem.Value.*
import y2017.Day25_HaltingProblem.part1

fun main(args: Array<String>) {
    part1()
}

object Day25_HaltingProblem {


    val part1 = challenge("Day 25 - Part One") {
        solve {
            val states = listOf(
                    State("A", isZero = {
                        write(One)
                        moveRight()
                        state = "B"
                    }, isOne = {
                        write(Zero)
                        moveLeft()
                        state = "C"
                    }),
                    State("B", isZero = {
                        write(One)
                        moveLeft()
                        state = "A"
                    }, isOne = {
                        write(One)
                        moveRight()
                        state = "D"
                    }),
                    State("C", isZero = {
                        write(One)
                        moveRight()
                        state = "A"
                    }, isOne = {
                        write(Zero)
                        moveLeft()
                        state = "E"
                    }),
                    State("D", isZero = {
                        write(One)
                        moveRight()
                        state = "A"
                    }, isOne = {
                        write(Zero)
                        moveRight()
                        state = "B"
                    }),
                    State("E", isZero = {
                        write(One)
                        moveLeft()
                        state = "F"
                    }, isOne = {
                        write(One)
                        moveLeft()
                        state = "C"
                    }),
                    State("F", isZero = {
                        write(One)
                        moveRight()
                        state = "D"
                    }, isOne = {
                        write(One)
                        moveRight()
                        state = "A"
                    })
            )

            result = TuringMachine(states, "A").checksum(12173597)
        }
    }

    data class State(
            val name: String,
            val isZero: TuringMachine.() -> Unit,
            val isOne: TuringMachine.() -> Unit
    )

    enum class Value { Zero, One }

    class TuringMachine(
            private val states: List<State>,
            var state: String
    ) {
        var tape = mutableMapOf<Long, Value>()
        var cursor = 0L
        val steps = 0L

        fun moveLeft() = cursor++
        fun moveRight() = cursor--

        fun write(value: Value) {
            tape[cursor] = value
        }

        fun checksum(steps: Long): Int {
            for (i in 1..steps) {
                val currentState = states.firstOrNull{ it.name == state }
                        ?: throw  IllegalStateException("Unknown state with name '$state'")
                val value = tape[cursor] ?: Zero
                when(value) {
                    Zero -> currentState.isZero(this)
                    One -> currentState.isOne(this)
                }
            }

            return tape.filter { it.value == One }.count()
        }
    }

}