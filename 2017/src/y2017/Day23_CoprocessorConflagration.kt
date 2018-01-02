package y2017

import com.pascalwelsch.aoc.challenge
import y2017.Day18_Duet.Instruction
import y2017.Day18_Duet.Processor
import y2017.Day18_Duet.parseInstruction
import y2017.Day23_CoprocessorConflagration.part1
import y2017.Day23_CoprocessorConflagration.part2

fun main(args: Array<String>) {
    part1()
    part2()
}

object Day23_CoprocessorConflagration {

    //--- Day 23: Coprocessor Conflagration ---
    //You decide to head directly to the CPU and fix the printer from there. As you get close, you find an experimental coprocessor doing so much work that the local programs are afraid it will halt and catch fire. This would cause serious issues for the rest of the computer, so you head in and see what you can do.
    //
    //The code it's running seems to be a variant of the kind you saw recently on that tablet. The general functionality seems very similar, but some of the instructions are different:
    //
    //set X Y sets register X to the value of Y.
    //sub X Y decreases register X by the value of Y.
    //mul X Y sets register X to the result of multiplying the value contained in register X by the value of Y.
    //jnz X Y jumps with an offset of the value of Y, but only if the value of X is not zero. (An offset of 2 skips the next instruction, an offset of -1 jumps to the previous instruction, and so on.)
    //Only the instructions listed above are used. The eight registers here, named a through h, all start at 0.
    //
    //The coprocessor is currently set to some kind of debug mode, which allows for testing, but prevents it from doing any meaningful work.
    //
    //If you run the program (your puzzle input), how many times is the mul instruction invoked?
    val part1 = challenge("Day 23 - Part 1") {
        solve {
            val instructions = inputFile("2017/23.txt").lines().map { parseInstruction(it) }
            result = Coprocessor(instructions).apply { run() }.mulCount //6241
        }
    }

    class Coprocessor(instructions: List<Instruction>, debug: Boolean = false) : Processor(instructions, 'a'..'h') {

        var mulCount = 0

        init {
            if (debug) mem["a"] = 1
        }

        override fun execute(instruction: Instruction): Boolean {
            when (instruction) {
                is Instruction.Multiply -> {
                    mulCount++
                }
            }
            return super.execute(instruction)
        }
    }


    val part2 = challenge("Day 23 - Part 2") {
        solve {
            result = solvePart2(false)
        }
    }

    //--- Part Two ---
    //Now, it's time to fix the problem.
    //
    //The debug mode switch is wired directly to register a. You flip the switch, which makes register a now start at 1 when the program is executed.
    //
    //Immediately, the coprocessor begins to overheat. Whoever wrote this program obviously didn't choose a very efficient implementation. You'll need to optimize the program if it has any hope of completing before Santa needs that printer working.
    //
    //The coprocessor's ultimate goal is to determine the final value left in register h once the program completes. Technically, if it had that... it wouldn't even need to run the program.
    //
    //After setting register a to 1, if the program were to run to completion, what value would be left in register h?
    fun solvePart2(debug: Boolean = false): Int {
        var a = if (debug) 1 else 0
        var b = 81 //1
        var c = b  //2
        var d: Int
        var f: Int
        var g: Int
        var h = 0
        if (a == 0) {
            b *= 100 //5
            b -= -100_000 //6
            c = b //7
            c -= -17_000 //8
        }
        do { //32
            f = 1 //9
            d = 2 //10
            while (d < b) {
                if (b % d == 0) {
                    f = 0 /// 16
                    break
                }
                d += 1 //21
            }
            if (f == 0) { //25
                h -= -1 //26
            }
            g = b //27
            g -= c //28
            if (g == 0) { // 29
                return h//30
                // result 909
            }
            b -= -17 // 31
        } while (true) //32
    }
}