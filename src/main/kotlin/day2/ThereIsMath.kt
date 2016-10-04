package day2

import common.solveFromInput


fun main(args: Array<String>) {
    solveFromInput("day2-1") { input, output ->

        val result = input.map { requiredPaper(it) }.sum()
        output.write("$result\n")
    }
}

/**
The elves are running low on wrapping paper, and so they need to submit an order for more. They have a list of the dimensions (length l, width w, and height h) of each present, and only want to order exactly as much as they need.

Fortunately, every present is a box (a perfect right rectangular prism), which makes calculating the required wrapping paper for each gift a little easier: find the surface area of the box, which is 2*l*w + 2*w*h + 2*h*l. The elves also need a little extra paper for each present: the area of the smallest side.

For example:

A present with dimensions 2x3x4 requires 2*6 + 2*12 + 2*8 = 52 square feet of wrapping paper plus 6 square feet of slack, for a total of 58 square feet.
A present with dimensions 1x1x10 requires 2*1 + 2*10 + 2*10 = 42 square feet of wrapping paper plus 1 square foot of slack, for a total of 43 square feet.
All numbers in the elves' list are in feet. How many total square feet of wrapping paper should they order?
 */
fun requiredPaper(dimensions: String): Int {
    val (rawL, rawW, rawH) = dimensions.split("x")
    val l = rawL.toInt()
    val w = rawW.toInt()
    val h = rawH.toInt()

    val x = l * w
    val y = w * h
    val z = h * l

    val surface = (2 * x) + (2 * y) + (2 * z)
    val smallestSide = listOf(x, y, z).min()!!

    return surface + smallestSide
}