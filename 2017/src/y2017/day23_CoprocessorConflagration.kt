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

    val part1 = challenge("Day 23 - Part 1") {
        inputFile("2017/23.txt")
        solveMultiLine {
            val instructions = it.map { parseInstruction(it) }
            result = Coprocessor(instructions).apply { run() }.mulCount
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
        solveMultiLine {
            result = solvePart2(true)
            //1001 too high
        }
    }

    fun solvePart2(debug: Boolean = false): Int {
        var a = if (debug) 1 else 0
        var b = 81 //1
        var c = b  //2
        var d = 0
        var e = 0
        var f = 0
        var g = 0
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
            do {
                e = 2 //11
                do {
                    g = d //12
                    g *= e //13
                    g -= b //14
                    if (g == 0) { //15
                        f = 0 //16
                    }
                    e -= -1//17
                    g = e //18
                    g -= b //19
                } while (g != 0) //20
                d -= -1 //21
                g = d //22
                g -= b //23
            } while (g != 0) //24
            if (f == 0) { //25
                h -= -1 //26
            }
            g = b //27
            g -= c //28
            if (g == 0) { // 29
                return h//30
            }
            b -= -17 // 31
        } while (true) //32
    }
}