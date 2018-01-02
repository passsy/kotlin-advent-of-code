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
    //--- Day 18: Duet ---
    //You discover a tablet containing some strange assembly code labeled simply "Duet". Rather than bother the sound card with it, you decide to run the code yourself. Unfortunately, you don't see any documentation, so you're left to figure out what the instructions mean on your own.
    //
    //It seems like the assembly is meant to operate on a set of registers that are each named with a single letter and that can each hold a single integer. You suppose each register should start with a value of 0.
    //
    //There aren't that many instructions, so it shouldn't be hard to figure out what they do. Here's what you determine:
    //
    //snd X plays a sound with a frequency equal to the value of X.
    //set X Y sets register X to the value of Y.
    //add X Y increases register X by the value of Y.
    //mul X Y sets register X to the result of multiplying the value contained in register X by the value of Y.
    //mod X Y sets register X to the remainder of dividing the value contained in register X by the value of Y (that is, it sets X to the result of X modulo Y).
    //rcv X recovers the frequency of the last sound played, but only when the value of X is not zero. (If it is zero, the command does nothing.)
    //jgz X Y jumps with an offset of the value of Y, but only if the value of X is greater than zero. (An offset of 2 skips the next instruction, an offset of -1 jumps to the previous instruction, and so on.)
    //Many of the instructions can take either a register (a single letter) or a number. The value of a register is the integer it contains; the value of a number is that number.
    //
    //After each jump instruction, the program continues with the instruction to which the jump jumped. After any other instruction, the program continues with the next instruction. Continuing (or jumping) off either end of the program terminates it.
    //
    //For example:
    //
    //set a 1
    //add a 2
    //mul a a
    //mod a 5
    //snd a
    //set a 0
    //rcv a
    //jgz a -1
    //set a 1
    //jgz a -2
    //The first four instructions set a to 1, add 2 to it, square it, and then set it to itself modulo 5, resulting in a value of 4.
    //Then, a sound with frequency 4 (the value of a) is played.
    //After that, a is set to 0, causing the subsequent rcv and jgz instructions to both be skipped (rcv because a is 0, and jgz because a is not greater than 0).
    //Finally, a is set to 1, causing the next jgz instruction to activate, jumping back two instructions to another jump, which jumps again to the rcv, which ultimately triggers the recover operation.
    //At the time the recover operation is executed, the frequency of the last sound played is 4.
    //
    //What is the value of the recovered frequency (the value of the most recently played sound) the first time a rcv instruction is executed with a non-zero value?
    val part1 = challenge("Day 18 - Part One") {
        solve {
            val instructions = inputFile("2017/18.txt").lines().map { parseInstruction(it) }
            result = SoloPlayer(instructions).apply { run() }.sounds.peek()!! //3423
        }
    }

    class SoloPlayer(instructions: List<Instruction>) : Processor(instructions) {
        var sounds = Stack<Int>()

        override fun execute(instruction: Instruction): Boolean {
            when (instruction) {
                is Snd -> sounds.push(instruction.register.value.toInt())
                is RecoverNotZero -> {
                    if (instruction.register.value != 0L) {
                        return true
                    }
                }
                else -> return super.execute(instruction)
            }

            return false
        }
    }

    abstract class Processor(
            private val instructions: List<Instruction>,
            registersNames: CharRange = 'a'..'z') {

        val mem = registersNames.associate { it.toString() to 0L }.toMutableMap()

        protected val ValueOrRegister.value: Long get() = intValue?.toLong() ?: register!!.value

        protected val String.value: Long get() = mem[this] ?: throw IllegalStateException("requested illegal register $this")

        // execute basic calculations on [mem]
        // returns true if the loop should break
        open protected fun execute(instruction: Instruction): Boolean {
            when (instruction) {
                is Set -> mem[instruction.register] = instruction.arg1.value
                is Add -> mem[instruction.register] = instruction.register.value + instruction.arg1.value
                is Sub -> mem[instruction.register] = instruction.register.value - instruction.arg1.value
                is Multiply -> mem[instruction.register] = instruction.register.value * instruction.arg1.value
                is Mod -> mem[instruction.register] = instruction.register.value % instruction.arg1.value
                is JumpGreaterZero -> {
                    if (instruction.arg0.value > 0) {
                        next += instruction.arg1.value.toInt() - 1
                    }
                    return false
                }
                is JumpNotZero -> {
                    if (instruction.arg0.value != 0L) {
                        next += instruction.arg1.value.toInt() - 1
                    }
                    return false
                }
                else -> throw IllegalArgumentException("can't handle $instruction")
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
            "sub" -> Sub(arg1, ValueOrRegister(arg2))
            "mul" -> Multiply(arg1, ValueOrRegister(arg2))
            "mod" -> Mod(arg1, ValueOrRegister(arg2))
            "rcv" -> RecoverNotZero(arg1)
            "jgz" -> JumpGreaterZero(ValueOrRegister(arg1), ValueOrRegister(arg2))
            "jnz" -> JumpNotZero(ValueOrRegister(arg1), ValueOrRegister(arg2))
            else -> throw IllegalArgumentException("Unknown instruction '$op'")
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

    //--- Part Two ---
    //As you congratulate yourself for a job well done, you notice that the documentation has been on the back of the tablet this entire time. While you actually got most of the instructions correct, there are a few key differences. This assembly code isn't about sound at all - it's meant to be run twice at the same time.
    //
    //Each running copy of the program has its own set of registers and follows the code independently - in fact, the programs don't even necessarily run at the same speed. To coordinate, they use the send (snd) and receive (rcv) instructions:
    //
    //snd X sends the value of X to the other program. These values wait in a queue until that program is ready to receive them. Each program has its own message queue, so a program can never receive a message it sent.
    //rcv X receives the next value and stores it in register X. If no values are in the queue, the program waits for a value to be sent to it. Programs do not continue to the next instruction until they have received a value. Values are received in the order they are sent.
    //Each program also has its own program ID (one 0 and the other 1); the register p should begin with this value.
    //
    //For example:
    //
    //snd 1
    //snd 2
    //snd p
    //rcv a
    //rcv b
    //rcv c
    //rcv d
    //Both programs begin by sending three values to the other. Program 0 sends 1, 2, 0; program 1 sends 1, 2, 1. Then, each program receives a value (both 1) and stores it in a, receives another value (both 2) and stores it in b, and then each receives the program ID of the other program (program 0 receives 1; program 1 receives 0) and stores it in c. Each program now sees a different value in its own copy of register c.
    //
    //Finally, both programs try to rcv a fourth time, but no data is waiting for either of them, and they reach a deadlock. When this happens, both programs terminate.
    //
    //It should be noted that it would be equally valid for the programs to run at different speeds; for example, program 0 might have sent all three values and then stopped at the first rcv before program 1 executed even its first instruction.
    //
    //Once both of your programs have terminated (regardless of what caused them to do so), how many times did program 1 send a value?
    val part2 = challenge("Day 18 - Part Two") {
        solve {
            val instructions = inputFile("2017/18.txt").lines().map { parseInstruction(it) }
            result = duet(instructions) //7493
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