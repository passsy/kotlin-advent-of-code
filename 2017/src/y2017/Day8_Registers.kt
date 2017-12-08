package y2017

import com.pascalwelsch.aoc.challenge
import y2017.Day8_Registers.Operator.DEC
import y2017.Day8_Registers.Operator.INC
import y2017.Day8_Registers.Sign.*

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
        val r = hashMapOf<String, Int>()

        fun execute(inst: Instruction) {
            var satisfied = false
            with(inst.condition) {
                val rValue = r[register] ?: 0
                satisfied = when (sign) {
                    GREATER -> rValue > value
                    SMALLER -> rValue < value
                    GREATER_EQUALS -> rValue >= value
                    SMALLER_EQUALS -> rValue <= value
                    EQUALS -> rValue == value
                    NOT_EQUALS -> rValue != value
                }

            }

            if (satisfied) {
                with(inst.operation) {
                    val rValue = r[register] ?: 0

                    r[register] = when (operator) {
                        INC -> rValue + value
                        DEC -> rValue - value
                    }
                }
            }
        }

        fun largestRegister(): Int {
            return r.values.max() ?: 0
        }
    }

    fun parseInstruction(input: String): Instruction {
        val values = "(\\w+)\\s(\\w+)\\s(-*\\d+)\\sif\\s(\\w+)\\s(.*)\\s(-*\\d+)"
                .toRegex().find(input)!!.groupValues

        val op = when (values[2]) {
            "inc" -> INC
            "dec" -> DEC
            else -> throw IllegalStateException("unknown operation ${values[2]}")
        }

        val operation = Operation(
                register = values[1],
                operator = op,
                value = values[3].toInt())

        val sign = when (values[5]) {
            ">" -> GREATER
            ">=" -> GREATER_EQUALS
            "<" -> SMALLER
            "<=" -> SMALLER_EQUALS
            "!=" -> NOT_EQUALS
            "==" -> EQUALS
            else -> throw IllegalStateException("unknown sign ${values[2]}")
        }

        val condition = Condition(
                register = values[4],
                sign = sign,
                value = values[6].toInt()
        )

        return Instruction(condition, operation)

    }

    data class Instruction(
            val condition: Condition,
            val operation: Operation
    )

    data class Operation(
            val register: String,
            val operator: Operator,
            val value: Int
    )

    data class Condition(
            val register: String,
            val sign: Sign,
            val value: Int
    )

    enum class Operator { INC, DEC }
    enum class Sign { GREATER, SMALLER, GREATER_EQUALS, SMALLER_EQUALS, EQUALS, NOT_EQUALS }

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
