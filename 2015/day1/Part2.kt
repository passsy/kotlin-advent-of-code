package y2015

import java.io.File

fun main(args: Array<String>) {
    val text = File("input").readText()
    println(whenEnterBasement(text))
}

/**
 * Now, given the same instructions, find the position of the first character that causes him to enter the basement (floor -1). The first character in the instructions has position 1, the second character has position 2, and so on.
 *
 * For example:
 *
 * `)` causes him to enter the basement at character position 1.
 * `()())` causes him to enter the basement at character position 5.
 * What is the position of the character that causes Santa to first enter the basement?
 */
fun whenEnterBasement(input: String): Int {
    val chars = input.toCharArray()

    var floor = 0
    chars.forEachIndexed { i, char ->
        when (char) {
            ')' -> {
                floor--
            }
            '(' -> {
                floor++
            }
        }

        if (floor == -1) {
            return i + 1
        }
    }

    throw IllegalStateException("never reached basement")
}
