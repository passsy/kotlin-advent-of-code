package day4

import common.solveFromInput
import java.math.BigInteger
import java.security.MessageDigest


fun main(args: Array<String>) {

    solveFromInput("day4-1") { input, out ->
        // 5 zeros
        val number = findAdventCoin(input[0]) { it.startsWith("00000") }
        out.write("$number")
    }

    solveFromInput("day4-2") { input, out ->
        // 6 zeros
        val number = findAdventCoin(input[0]) { it.startsWith("000000") }
        out.write("$number")
    }
}


/**
Santa needs help mining some AdventCoins (very similar to bitcoins) to use as gifts for all the economically forward-thinking little girls and boys.

To do this, he needs to find MD5 hashes which, in hexadecimal, start with at least five zeroes. The input to the MD5 hash is some secret key (your puzzle input, given below) followed by a number in decimal. To mine AdventCoins, you must find Santa the lowest positive number (no leading zeroes: 1, 2, 3, ...) that produces such a hash.

--- Part Two ---

Now find one that starts with six zeroes.
 */
fun findAdventCoin(input: String, condition: (string: String) -> Boolean): Int {
    for (i in 0..100000000) {
        val probe = "$input$i".md5()
        if (condition(probe)) {
            return i
        }
    }
    throw IndexOutOfBoundsException("could not find coin in 100mio tries")
}


private fun String.md5(): String {
    val digest = MessageDigest.getInstance("MD5")
            .digest(this.toByteArray(Charsets.UTF_8))
    val hexMd5 = BigInteger(1, digest).toString(16)
    return hexMd5.padStart(32, '0')
}