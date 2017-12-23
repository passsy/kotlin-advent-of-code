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
            result = SoloPlayer(instructions).apply { run() }.sounds.peek()!!
        }
    }

    class SoloPlayer(instructions: List<Instruction>) : Processor(instructions) {
        var sounds = Stack<Int>()

        override fun execute(instr: Instruction): Boolean {
            when (instr) {
                is Snd -> sounds.push(instr.register.value.toInt())
                is JumpGreaterZero -> {
                    if (instr.arg0.value > 0) {
                        next += instr.arg1.value.toInt() - 1
                    }
                    return false
                }
                is RecoverNotZero -> {
                    if (instr.register.value != 0L) {
                        return true
                    }
                }
                else -> return super.execute(instr)
            }

            return false
        }
    }

    abstract class Processor(
            private val instructions: List<Instruction>,
            registersNames: CharRange = 'a'..'z') {

        protected val mem = registersNames.associate { it.toString() to 0L }.toMutableMap()

        protected val ValueOrRegister.value: Long get() = intValue?.toLong() ?: register!!.value

        protected val String.value: Long get() = mem[this] ?: throw IllegalStateException("requested illegal register $this")

        // execute basic calculations on [mem]
        // returns true if the loop should break
        open protected fun execute(instr: Instruction): Boolean {
            when (instr) {
                is Set -> mem[instr.register] = instr.arg1.value
                is Add -> mem[instr.register] = instr.register.value + instr.arg1.value
                is Sub -> mem[instr.register] = instr.register.value - instr.arg1.value
                is Multiply -> mem[instr.register] = instr.register.value * instr.arg1.value
                is Mod -> mem[instr.register] = instr.register.value % instr.arg1.value
                else -> throw IllegalArgumentException("can't handle $instr")
            }

            return false
        }

        protected var next = 0
        open var terminated: Boolean = false


        fun run() {
            if (terminated) return
            loop@ while (next >= 0 && next < instructions.size) {
                val instr = instructions[next]
                val shouldBreak = execute(instr)
                if (shouldBreak) {
                    break@loop
                }
                next++
            }

            terminated = true
        }

    }

    fun parseInstruction(input: String): Instruction {
        val (_, op, arg1, arg2) = "(\\w{3}) (\\w+) ?(\\S+)*".toRegex().find(input)!!.groupValues

        return when (op) {
            "snd" -> Snd(ValueOrRegister(arg1))
            "set" -> Set(arg1, ValueOrRegister(arg2))
            "add" -> Add(arg1, ValueOrRegister(arg2))
            "sum" -> Sub(arg1, ValueOrRegister(arg2))
            "mul" -> Multiply(arg1, ValueOrRegister(arg2))
            "mod" -> Mod(arg1, ValueOrRegister(arg2))
            "rcv" -> RecoverNotZero(arg1)
            "jgz" -> JumpGreaterZero(ValueOrRegister(arg1), ValueOrRegister(arg2))
            "jnz" -> JumpNotZero(ValueOrRegister(arg1), ValueOrRegister(arg2))
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
        data class Sub(val register: String, val arg1: ValueOrRegister) : Instruction()
        data class Multiply(val register: String, val arg1: ValueOrRegister) : Instruction()
        data class Mod(val register: String, val arg1: ValueOrRegister) : Instruction()
        data class RecoverNotZero(val register: String) : Instruction()
        data class JumpGreaterZero(val arg0: ValueOrRegister, val arg1: ValueOrRegister) : Instruction()
        data class JumpNotZero(val arg0: ValueOrRegister, val arg1: ValueOrRegister) : Instruction()
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
            players.values.forEach { it.run() }
        }

        return players[1]!!.valuesSent
    }

    class SyncedPlayer(
            val id: Long,
            instructions: List<Instruction>,
            private val notify: (senderId: Long, value: Long) -> Unit
    ) : Processor(instructions) {

        val queue = LinkedBlockingDeque<Long>()

        var waiting: Boolean = false
        var valuesSent = 0

        override var terminated: Boolean = false
            get() = super.terminated
            set(value) {
                if (!waiting) {
                    field = value
                }
            }

        init {
            mem["p"] = id
        }

        override fun execute(instruction: Instruction): Boolean {
            when (instruction) {
                is Snd -> {
                    valuesSent++
                    val value = instruction.register.value
                    notify(id, value)
                }
                is JumpGreaterZero -> {
                    if (instruction.arg0.value > 0) {
                        next += instruction.arg1.value.toInt()
                    } else {
                        next++
                    }
                    return true
                }
                is RecoverNotZero -> {
                    val element = queue.poll()
                    if (element != null) {
                        mem[instruction.register] = element
                    } else {
                        waiting = true
                        return true
                    }
                }
                else -> return super.execute(instruction)
            }
            return false
        }
    }
}