package y2017

import com.pascalwelsch.aoc.challenge
import y2017.Day18_Duet.Instruction.*
import y2017.Day18_Duet.Instruction.Set
import y2017.Day18_Duet.part1
import y2017.Day18_Duet.part2
import java.util.*
import java.util.concurrent.LinkedBlockingDeque


fun main(args: Array<String>) {
    part1()
    part2()
}

object Day18_Duet {

    val part1 = challenge("Day 18 - Part One") {
        inputFile("2017/18.txt")
        solveMultiLine {
            val instructions = it.map { parseInstruction(it) }
            result = SoloPlayer(instructions).apply { play() }.sounds.peek()!!
        }
    }

    class SoloPlayer(private val instructions: List<Instruction>) : Player() {
        var sounds = Stack<Int>()

        fun play() {
            var next = 0

            loop@ while (next >= 0 && next < instructions.size) {
                val instr = instructions[next]

                when (instr) {
                    is Snd -> sounds.push(instr.register.value.toInt())
                    is JumpGreaterZero -> {
                        if (instr.arg0.value > 0) {
                            next += instr.arg1.value.toInt()
                        } else {
                            next++
                        }
                        continue@loop
                    }
                    is RecoverNotZero -> {
                        if (instr.register.value != 0L) {
                            return
                        }
                    }
                    else -> calculate(instr)
                }
                next++
            }
        }
    }

    abstract class Player {

        protected val mem = ('a'..'z').associate { it.toString() to 0L }.toMutableMap()

        protected val ValueOrRegister.value: Long get() = intValue?.toLong() ?: register!!.value

        protected val String.value: Long get() = mem[this] ?: throw IllegalStateException("requested illegal register $this")

        // execute basic calculations on [mem]
        protected fun calculate(instr: Instruction) = when (instr) {
            is Set -> mem[instr.register] = instr.arg1.value
            is Add -> mem[instr.register] = instr.register.value + instr.arg1.value
            is Multiply -> mem[instr.register] = instr.register.value * instr.arg1.value
            is Mod -> mem[instr.register] = instr.register.value % instr.arg1.value
            else -> throw IllegalArgumentException("can't handle $instr")
        }
    }

    fun parseInstruction(input: String): Instruction {
        val (_, op, arg1, arg2) = "(\\w{3}) (\\w+) ?(\\S+)*".toRegex().find(input)!!.groupValues

        return when (op) {
            "snd" -> Snd(ValueOrRegister(arg1))
            "set" -> Set(arg1, ValueOrRegister(arg2))
            "add" -> Add(arg1, ValueOrRegister(arg2))
            "mul" -> Multiply(arg1, ValueOrRegister(arg2))
            "mod" -> Mod(arg1, ValueOrRegister(arg2))
            "rcv" -> RecoverNotZero(arg1)
            "jgz" -> JumpGreaterZero(ValueOrRegister(arg1), ValueOrRegister(arg2))
            else -> throw IllegalArgumentException("Unknown instruction $op")
        }
    }

    class ValueOrRegister(private val rawString: String) {
        val intValue: Int? = try {
            rawString.toInt()
        } catch (e: NumberFormatException) {
            null
        }
        val register: String?

        init {
            register = if (intValue == null) rawString else null
        }

        override fun toString(): String = rawString
    }

    sealed class Instruction {
        data class Snd(val register: ValueOrRegister) : Instruction()
        data class Set(val register: String, val arg1: ValueOrRegister) : Instruction()
        data class Add(val register: String, val arg1: ValueOrRegister) : Instruction()
        data class Multiply(val register: String, val arg1: ValueOrRegister) : Instruction()
        data class Mod(val register: String, val arg1: ValueOrRegister) : Instruction()
        data class RecoverNotZero(val register: String) : Instruction()
        data class JumpGreaterZero(val arg0: ValueOrRegister, val arg1: ValueOrRegister) : Instruction()
    }


    val part2 = challenge("Day 18 - Part Two") {
        inputFile("2017/18.txt")
        solveMultiLine {
            val instructions = it.map { parseInstruction(it) }
            result = duet(instructions)
        }
    }

    fun duet(instructions: List<Instruction>): Int {
        val players = hashMapOf<Long, SyncedPlayer>()

        fun notify(senderId: Long, value: Long) {
            // assume we have only two players
            val other = players.values.first { it.id != senderId }
            other.run {
                queue.add(value)
                waiting = false
            }
        }

        SyncedPlayer(0, instructions, ::notify).also { players.put(it.id, it) }
        SyncedPlayer(1, instructions, ::notify).also { players.put(it.id, it) }


        while (players.values.any { !it.waiting && !it.terminated }) {
            players.values.forEach { it.play() }
        }

        return players[1]!!.valuesSent
    }

    class SyncedPlayer(val id: Long,
                       private val instructions: List<Instruction>,
                       private val notify: (senderId: Long, value: Long) -> Unit
    ) : Player() {

        val queue = LinkedBlockingDeque<Long>()

        var waiting: Boolean = false
        var terminated: Boolean = false
        var valuesSent = 0

        init {
            mem["p"] = id
        }

        private var next = 0

        fun play() {
            if (terminated) return

            loop@ while (next >= 0 && next < instructions.size) {
                val instr = instructions[next]

                when (instr) {
                    is Snd -> {
                        valuesSent++
                        val value = instr.register.value
                        notify(id, value)
                    }
                    is JumpGreaterZero -> {
                        if (instr.arg0.value > 0) {
                            next += instr.arg1.value.toInt()
                        } else {
                            next++
                        }
                        continue@loop
                    }
                    is RecoverNotZero -> {
                        val element = queue.poll()
                        if (element != null) {
                            mem[instr.register] = element
                        } else {
                            waiting = true
                            return
                        }
                    }
                    else -> calculate(instr)
                }
                next++
            }
            terminated = true
        }
    }
}