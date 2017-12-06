package y2015

fun main(args: Array<String>) {
    solveFromInput("day1-1") { input, output ->
        val result = whatFloor(input[0])
        output.write("$result")
    }

    solveFromInput("day1-2") { input, output ->
        val step = whenEnterBasement(input[0])
        output.write("$step")
    }
}

/**
 * Santa is trying to deliver presents in a large apartment building, but he can't find the right floor - the directions he got are a little confusing. He starts on the ground floor (floor 0) and then follows the instructions one character at a time.
 *
 * An opening parenthesis, (, means he should go up one floor, and a closing parenthesis, ), means he should go down one floor.
 *
 * The apartment building is very tall, and the basement is very deep; he will never find the top or bottom floors.
 *
 * For example:
 *
 * `(())` and `()()` both result in floor 0.
 * `(((` and `(()(()(` both result in floor 3.
 * `))(((((` also results in floor 3.
 * `())` and `))(` both result in floor -1 (the first basement level).
 * `)))` and `)())())` both result in floor -3.
 * To what floor do the instructions take Santa?
 */
fun whatFloor(input: String): Int {
    val chars = input.toCharArray()
    val down = chars.filter { it == ')' }.count()
    val up = chars.filter { it == '(' }.count()
    return up - down
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
