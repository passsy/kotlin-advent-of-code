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
        return _solve(requireNotNull(solver))
    }

    /**
     * like [invoke] but disables writing the output in a file
     */
    fun test(): String {
        outFile = null
        return _solve(requireNotNull(solver))
    }

    private var input: (() -> String)? = null
    private var outFile: (() -> File)? = null
    private var solver: Solver<*>? = null

    fun ChallengeSolver.inputText(text: String, trim: Boolean = true) {
        input = {
            val trimmed = if (trim) {
                text.trimChallengeInput()
            } else {
                text
            }
            if (trimmed.isEmpty()) throw IllegalArgumentException("Input is empty")
            println("input has ${text.lines().size} lines")
            trimmed
        }
    }

    fun ChallengeSolver.inputFile(path: String, trim: Boolean = true) {
        input = { resourceFileText(path, trim)}
    }

    fun ChallengeSolver.outputFile(path: String) {
        outFile = {
            val out = File("out/$path")
            out.parentFile.mkdirs()
            out
        }
    }

    fun solveMultiLine(block: Result.(List<String>) -> Unit) {
        solver = Solver({ requireNotNull(input)().lines() }, block)
    }

    fun solve(block: Result.(String) -> Unit) {
        solver = Solver({ requireNotNull(input)() }, block)
    }

    private fun <T> _solve(solver: Solver<T>): String {
        val title = "\nChallenge '$name'"
        val line = "".padStart(title.count(), '=')
        println("$title\n$line")

        val context = Result()
        val input = solver.inputProvider()

        println("\nsolving...")
        val duration = measureTimeMillis {
            solver.solve(context, input)
        }

        // print output partially because the can get very long
        println("\ncom.pascalwelsch.aoc.Result of '$name':")
        val result = context.result.toString()
        if (result.isEmpty()) {
            throw IllegalStateException("No Result! output is empty")
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

class Solver<T>(
        val inputProvider: () -> T,
        val block: Result.(T) -> Unit
) {
    fun solve(context: Result, input: T) = block(context, input)
}


/**
 * Has help methods while solving a challenge
 */
class Result {

    /**
     * use `result = "solution"` to append text to the final solution
     */
    var result: Any = ""

}

private const val MAX_RESULT_PRINT_LINES = 10

fun List<String>.trimResults(maxLineCount: Int): List<String> {
    if (this.size < maxLineCount) return this
    return take(maxLineCount) + "... <${this.size - maxLineCount} more lines>" + "$size lines"
}