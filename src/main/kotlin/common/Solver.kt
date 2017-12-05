package common

import java.awt.SystemColor.text
import java.io.File
import java.io.FileNotFoundException
import kotlin.system.measureTimeMillis

fun challenge(name: String, init: ChallengeSolver.() -> Unit) = ChallengeSolver(name).apply(init)

@DslMarker
annotation class CodingChallenge

@CodingChallenge
class ChallengeSolver(private var name: String = "Challenge") {
    private var input: String? = null
    private var outFile: File? = null

    init {
        val title = "\nSolving '$name'..."
        val line = "".padStart(title.count(), '=')
        println("$title\n$line")
    }

    fun ChallengeSolver.inputText(text: String, trim: Boolean = true) {
        val trimmed = if (trim) {
            text.trimChallengeInput()
        } else {
            text
        }
        if (trimmed.isEmpty()) throw IllegalArgumentException("Input is empty")
        println("input has ${text.lines().size} lines")
        input = trimmed
    }

    fun ChallengeSolver.inputFile(path: String, trim: Boolean = true) {
        val file = File(Int::class.java.getResource(path).toURI())
        println("reading input file '${file.absolutePath}'")
        if (!file.exists()) {
            throw FileNotFoundException("couldn't find input file '$path' at ${file.absolutePath}")
        }
        val text = file.readText()
        if (text.isEmpty()) throw IllegalArgumentException("Input file is empty")
        println("input file '$path' has ${text.lines().size} lines")
        input = if (!trim) {
            text
        } else {
            // by default the whitespace at of badly copied text should be trimmed.
            text.trim().trimIndent()
        }
    }

    fun ChallengeSolver.outputFile(path: String) {
        outFile = File("out/$path")
    }


    fun solveMultiLine(solveFunction: Solver.(List<String>) -> Unit) {
        val lines = requireNotNull(input).lines()
        Solver(name, outFile).solve({ solveFunction(lines) })
    }

    fun solve(solveFunction: Solver.(String) -> Unit) {
        val input = requireNotNull(input)
        Solver(name, outFile).solve({ solveFunction(input) })
    }
}

/**
 * trim the text matching typical challenge inputs, remove empty lines at start and bottom and removes indention.
 */
private fun String.trimChallengeInput():String{
    return lines()
            .dropWhile { it.isBlank() }
            .dropLastWhile { it.isBlank() }
            .joinToString("\n")
            .trimIndent()
}

class Solver(
        private val name: String,
        private var outFile: File? = null
) {
    /**
     * use `result = "solution"` to append text to the final solution
     */
    var result: Any
        get() = "\n"
        set(value) {
            out.append(value)
        }

    private val out = StringBuilder()

    fun solve(solve: Solver.() -> Unit) {
        println("\nsolving...")
        val duration = measureTimeMillis {
            solve.invoke(this)
        }

        // print output partially because the can get very long
        println("\nResult of '$name':")
        val result = out.toString()
        if (result.isEmpty()) {
            throw IllegalStateException("No output! result is empty")
        }

        result.lines().trimResults(MAX_RESULT_PRINT_LINES).forEach { println(it) }

        println("\ntook: ${duration / 1000.0}s")

        // optionally write result to file
        outFile?.let { output ->
            output.parentFile.mkdirs()
            val out = output.bufferedWriter()
            out.write(result)
            println("writing ${result.lines().size} lines to\n\n${output.absoluteFile}")
            out.close()
        }
        println("\n")
    }
}


private const val MAX_RESULT_PRINT_LINES = 10

fun List<String>.trimResults(maxLineCount: Int): List<String> {
    if (this.size < maxLineCount) return this
    return take(maxLineCount) + "... <${this.size - maxLineCount} more lines>" + "$size lines"
}


