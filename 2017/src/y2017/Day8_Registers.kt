package y2017

import com.pascalwelsch.aoc.challenge

fun main(args: Array<String>) {
    Day8_Registers.part1()
    Day8_Registers.part2()
}

object Day8_Registers {
    //--- Day 8: I Heard You Like Registers ---
    //
    //You receive a signal directly from the CPU. Because of your recent assistance with jump instructions, it would like you to compute the result of a series of unusual register instructions.
    //
    //Each instruction consists of several parts: the register to modify, whether to increase or decrease that register's value, the amount by which to increase or decrease it, and a condition. If the condition fails, skip the instruction without modifying the register. The registers all start at 0. The instructions look like this:
    //
    //b inc 5 if a > 1
    //a inc 1 if b < 5
    //c dec -10 if a >= 1
    //c inc -20 if c == 10
    //These instructions would be processed as follows:
    //
    //Because a starts at 0, it is not greater than 1, and so b is not modified.
    //a is increased by 1 (to 1) because b is less than 5 (it is 0).
    //c is decreased by -10 (to 10) because a is now greater than or equal to 1 (it is 1).
    //c is increased by -20 (to -10) because c is equal to 10.
    //After this process, the largest value in any register is 1.
    //
    //You might also encounter <= (less than or equal to) or != (not equal to). However, the CPU doesn't have the bandwidth to tell you what all the registers are named, and leaves that to you to determine.
    //
    //What is the largest value in any register after completing the instructions in your puzzle input?
    val part1 = challenge("Day 8 - Part One") {
        inputFile("2017/8.txt")

        solveMultiLine {
            val instructions = it.map { parseInstruction(it) }
            val r = Register()
            instructions.forEach { r.execute(it) }
            result = r.largestRegister()
        }
    }

    class Register {
        private val r = hashMapOf<String, Int>()

        private fun registerValue(register: String) = r[register] ?: 0

        fun execute(inst: Instruction) {

            if (inst.condition.verify(::registerValue)) {
                val register = inst.operation.register
                r[register] = inst.operation.execute(registerValue(register))
            }
        }

        fun largestRegister(): Int {
            return r.values.max() ?: 0
        }
    }

    fun parseInstruction(input: String): Instruction {
        val values = "(\\w+)\\s(\\w+)\\s(-*\\d+)\\sif\\s(\\w+)\\s(.*)\\s(-*\\d+)"
                .toRegex().find(input)!!.groupValues

        val op: Operator = when (values[2]) {
            "inc" -> Operator.INC
            "dec" -> Operator.DEC
            else -> throw IllegalStateException("unknown operation ${values[2]}")
        }
        val operation = Operation(values[1], op, values[3].toInt())

        val verification: Verification = when (values[5]) {
            ">" -> Verification.GREATER
            ">=" -> Verification.GREATER_EQUALS
            "<" -> Verification.SMALLER
            "<=" -> Verification.SMALLER_EQUALS
            "==" -> Verification.EQUALS
            "!=" -> Verification.NOT_EQUALS
            else -> throw IllegalStateException("unknown sign ${values[2]}")
        }
        val condition = Condition(values[4], verification, values[6].toInt())

        return Instruction(condition, operation)

    }

    data class Instruction(val condition: Condition, val operation: Operation)
    data class Operation(
            val register: String,
            private val operator: Operator,
            private val value: Int) {
        fun execute(registerValue: Int) = operator.execute(registerValue, value)
    }

    data class Condition(
            private val register: String,
            private val verification: Verification,
            private val value: Int) {
        fun verify(registerValue: (register: String) -> Int) =
                verification.verify(registerValue(register), value)
    }

    enum class Operator(val execute: (Int, Int) -> Int) {
        INC({ a, b -> a + b }),
        DEC({ a, b -> a - b })
    }

    enum class Verification(val verify: (Int, Int) -> Boolean) {
        GREATER({ a, b -> a > b }),
        SMALLER({ a, b -> a < b }),
        GREATER_EQUALS({ a, b -> a >= b }),
        SMALLER_EQUALS({ a, b -> a <= b }),
        @Suppress("UnusedEquals")
        EQUALS({ a, b -> a == b }),
        NOT_EQUALS({ a, b -> a != b })
    }


    //--- Part Two ---
    //
    //To be safe, the CPU also needs to know the highest value held in any register during this process so that it can decide how much memory to allocate to these operations. For example, in the above instructions, the highest value ever held was 10 (in register c after the third instruction was evaluated).
    val part2 = challenge("Day 8 - Part Two") {
        inputFile("2017/8.txt")

        solveMultiLine {
            val instructions = it.map { parseInstruction(it) }
            val r = Register()
            var largestValue = 0
            instructions.forEach {
                r.execute(it)
                largestValue = Math.max(r.largestRegister(), largestValue)
            }
            result = largestValue
        }
    }
}
