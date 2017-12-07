package y2016

import com.pascalwelsch.aoc.challenge
import y2016.Day3_ThreeSidedSquares.part1
import y2016.Day3_ThreeSidedSquares.part2


fun main(args: Array<String>) {
    part1()
    part2()
}

object Day3_ThreeSidedSquares {

    //--- Day 3: Squares With Three Sides ---
    //
    //Now that you can think clearly, you move deeper into the labyrinth of hallways and office furniture that makes up this part of Easter Bunny HQ. This must be a graphic design department; the walls are covered in specifications for triangles.
    //
    //Or are they?
    //
    //The design document gives the side lengths of each triangle it describes, but... 5 10 25? Some of these aren't triangles. You can't help but mark the impossible ones.
    //
    //In a valid triangle, the sum of any two sides must be larger than the remaining side. For example, the "triangle" given above is impossible, because 5 + 10 is not larger than 25.
    //
    //In your puzzle input, how many of the listed triangles are possible?
    val part1 = challenge("Day 3 - Part One") {
        inputFile("2016/3.txt")

        solveMultiLine {
            result = it.map { Triangle.parse(it) }
                    .filter { it.isPossible() }
                    .count()
        }
    }

    data class Triangle(val a: Int, val b: Int, val c: Int) {
        fun isPossible(): Boolean = a + b > c && b + c > a && c + a > b

        companion object {
            fun parse(input: String): Triangle {
                val (a, b, c) = input.split("\\s+".toRegex())
                        .filter { it.isNotBlank() }
                        .map { it.toInt() }
                return Triangle(a, b, c)
            }
        }
    }

    //--- Part Two ---
    //
    //Now that you've helpfully marked up their design documents, it occurs to you that triangles are specified in groups of three vertically. Each set of three numbers in a column specifies a triangle. Rows are unrelated.
    //
    //For example, given the following specification, numbers with the same hundreds digit would be part of the same triangle:
    //
    //101 301 501
    //102 302 502
    //103 303 503
    //201 401 601
    //202 402 602
    //203 403 603
    //In your puzzle input, and instead reading by columns, how many of the listed triangles are possible?
    val part2 = challenge("Day 3 - Part Two") {
        inputFile("2016/3.txt")
        solve {
            result = it.lines()
                    .map { Triangle.parse(it) }
                    .verticalTriangles()
                    .filter { it.isPossible() }
                    .count()
        }
    }


}
fun List<Day3_ThreeSidedSquares.Triangle>.verticalTriangles() = windowed(3, step = 3)
        .flatMap {
            listOf(Day3_ThreeSidedSquares.Triangle(it[0].a, it[1].a, it[2].a),
                    Day3_ThreeSidedSquares.Triangle(it[0].b, it[1].b, it[2].b),
                    Day3_ThreeSidedSquares.Triangle(it[0].c, it[1].c, it[2].c))
        }
