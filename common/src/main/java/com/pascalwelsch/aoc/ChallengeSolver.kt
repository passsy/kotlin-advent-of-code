package com.pascalwelsch.aoc

import java.io.File
import kotlin.system.measureTimeMillis

fun challenge(name: String, init: ChallengeSolver.() -> Unit) = ChallengeSolver(name).apply(init)

@DslMarker
annotation class CodingChallenge

/**
 * helps providing input data
 */
@CodingChallenge
class ChallengeSolver(var name: String = "Challenge") {

    operator fun invoke(): String {
        return _solve()
    }

    /**
     * like [invoke] but disables writing the output in a file
     */
    fun test(): String {
        outFile = null
        return _solve()
    }

    private var outFile: (() -> File)? = null
    private var solver: (Solving.() -> Unit)? = null

    fun ChallengeSolver.outputFile(path: String) {
        outFile = {
            val out = File("out/$path")
            out.parentFile.mkdirs()
            out
        }
    }

    fun solve(block: Solving.() -> Unit) {
        solver = block
    }

    private fun _solve(): String {
        val title = "\nChallenge '$name'"
        val line = "".padStart(title.count(), '=')
        println("$title\n$line")

        val context = Solving()

        println("\nsolving...")
        val duration = measureTimeMillis {
            solver!!.invoke(context)
        }

        // print output partially because the can get very long
        println("\nResult of '$name':")
        val result = context.result.toString()
        if (result.isEmpty()) {
            println("No Result! Output is empty :(")
        }

        result.lines().trimResults(MAX_RESULT_PRINT_LINES).forEach { println(it) }

        println("\ntook: ${duration / 1000.0}s")

        // optionally write result to file
        outFile?.invoke()?.let { file ->
            val out = file.bufferedWriter()
            out.write(result)
            println("writing ${result.lines().size} lines to\n\n${file.absoluteFile}")
            out.close()
        }

        println('\n')
        return result
    }

}

/**
 * Has help methods while solving a challenge
 */
class Solving {

    /**
     * use `result = "solution"` to append text to the final solution
     */
    var result: Any = ""

    fun inputFile(path: String, trim: Boolean = true): String {
        return resourceFileText(path, trim)
    }
}

private const val MAX_RESULT_PRINT_LINES = 10

fun List<String>.trimResults(maxLineCount: Int): List<String> {
    if (this.size < maxLineCount) return this
    return take(maxLineCount) + "... <${this.size - maxLineCount} more lines>" + "$size lines"
}