fun main(args: Array<String>) {

    solveFromInput("day5-1") { input, out ->
        val niceCount = input.filter { it.isNice() }.count()
        out.write("$niceCount")
    }

    solveFromInput("day5-2") { input, out ->
        val nicerCount = input.filter { it.isNicer() }.count()
        out.write("$nicerCount")
    }
}


/**
Santa needs help figuring out which strings in his text file are naughty or nice.

A nice string is one with all of the following properties:

It contains at least three vowels (aeiou only), like aei, xazegov, or aeiouaeiouaeiou.
It contains at least one letter that appears twice in a row, like xx, abcdde (dd), or aabbccdd (aa, bb, cc, or dd).
It does not contain the strings ab, cd, pq, or xy, even if they are part of one of the other requirements.
 */
fun String.isNice(): Boolean {
    if (length < 3) {
        return false
    }

    if (contains("ab") || contains("cd") || contains("pq") || contains("xy")) {
        return false
    }

    if (!hasDouble()) {
        return false
    }

    if (!hasVowels(3)) {
        return false
    }

    return true
}

fun String.hasDouble(): Boolean {
    var prev = ';'

    for (char in toCharArray()) {
        if (char == prev) {
            return true
        }
        prev = char
    }
    return false
}

/**
 * [minCount] minimum number of vowels
 */
fun String.hasVowels(minCount: Int = 1): Boolean {
    require(minCount > 0) { "minCount has to be at least 1" }

    var a = 0
    var e = 0
    var i = 0
    var o = 0
    var u = 0

    for (char in toCharArray()) {
        when (char) {
            'a' -> a++
            'e' -> e++
            'i' -> i++
            'o' -> o++
            'u' -> u++
        }

        if (a + e + i + o + u >= minCount) {
            return true
        }
    }
    return false
}

/**
Realizing the error of his ways, Santa has switched to a better model of determining whether a string is naughty or nice. None of the old rules apply, as they are all clearly ridiculous.

Now, a nice string is one with all of the following properties:

It contains a pair of any two letters that appears at least twice in the string without overlapping, like xyxy (xy) or aabcdefgaa (aa), but not like aaa (aa, but it overlaps).
It contains at least one letter which repeats with exactly one letter between them, like xyx, abcdefeghi (efe), or even aaa.
 */
fun String.isNicer(): Boolean {
    if (length < 3) {
        return false
    }

    if (!containsSandwichChars()) {
        return false
    }

    if (!containsDoublePairs()) {
        return false
    }

    return true
}

/**
 * [true] when it contains a pair of any two letters that appears at least twice in the string without overlapping, like xyxy (xy) or aabcdefgaa (aa), but not like aaa (aa, but it overlaps).
 */
fun String.containsDoublePairs(): Boolean {
    val chars = toCharArray()
    for (i in 0..chars.size - 4) {
        val probe = substring(i, i + 2)
        val rest = substring(i + 2)
        if (rest.contains(probe)) {
            return true
        }
    }
    return false
}

/**
 * [true] when it contains at least one letter which repeats with exactly one letter between them, like xyx, abcdefeghi (efe), or even aaa.
 */
fun String.containsSandwichChars(): Boolean {
    var first: Char? = null
    var middle: Char? = null

    for (char in toCharArray()) {
        if (char == first) {
            return true
        }
        first = middle
        middle = char
    }
    return false
}