package y2015.day8

import y2015.common.solveFromInput
import org.apache.commons.lang.StringEscapeUtils


fun main(args: Array<String>) {

    solveFromInput("day8-1") { input, output ->
        val escaped = input[0]
        val unescape = StringEscapeUtils.unescapeJava(escaped)
        val result = escaped.length - unescape.length
        println("$result")
    }
    solveFromInput("day8-1") { input, output ->
        val escaped = "\"\\x27\""
        val unescape = StringEscapeUtils.unescapeJava(escaped)
        val result = escaped.length - unescape.length
        println("$result")
    }
}