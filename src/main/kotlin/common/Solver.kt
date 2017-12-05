package common

import java.io.File
import java.io.FileNotFoundException
import kotlin.system.measureTimeMillis

fun challenge(name: String, init: ChallengeSolver.() -> Unit) = ChallengeSolver(name).apply(init)

@DslMarker
annotation class CodingChallenge

/**
 * helps providing input data
 */
@CodingChallenge
class ChallengeSolver(var name: String = "Challenge") {

    operator fun invoke() {
        _solve(requireNotNull(solver))
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
        input = {
            val file = File(Int::class.java.getResource(path).toURI())
            println("reading input file '${file.absolutePath}'")
            if (!file.exists()) {
                throw FileNotFoundException(
                        "couldn't find input file '$path' at ${file.absolutePath}")
            }
            val text = file.readText()
            if (text.isEmpty()) throw IllegalArgumentException("Input file is empty")
            println("input file '$path' has ${text.lines().size} lines")
            val trimmed = if (trim) {
                text.trimChallengeInput()
            } else {
                text
            }
            trimmed
        }
    }

    fun ChallengeSolver.outputFile(path: String) {
        outFile = {
            val out = File("out/$path")
            out.parentFile.mkdirs()
            out
        }
    }

    fun solveMultiLine(block: Result.(List<String>) -> Unit) {
        solver = Solver({ requireNotNull(input)().lines() }, {  context, input -> block(context, input) })

    }

    fun solve(block: Result.(String) -> Unit) {
        solver = Solver({ requireNotNull(input)() }, { context, input -> block(context, input) })
    }

    private  fun <T> _solve(solver: Solver<T>) {
        val title = "\nSolving '$name'..."
        val line = "".padStart(title.count(), '=')
        println("$title\n$line")

        val context = Result()
        val input = solver.inputLoader()

        println("\nsolving...")
        val duration = measureTimeMillis {
            solver.solve(context, input)
        }

        // print output partially because the can get very long
        println("\nResult of '$name':")
        val result = context.result.toString()
        if (result.isEmpty()) {
            throw IllegalStateException("No output! result is empty")
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
    }

}

class Solver<T>(
        val inputLoader: () -> T,
        val solve: (Result, T) -> Unit
)


/**
 * Has help methods while solving a challenge
 */
class Result {

    /**
     * use `result = "solution"` to append text to the final solution
     */
    var result: Any = ""

}

/**
 * trim the text matching typical challenge inputs, remove empty lines at start and bottom and removes indention.
 */
private fun String.trimChallengeInput(): String {
    return lines()
            .dropWhile { it.isBlank() }
            .dropLastWhile { it.isBlank() }
            .joinToString("\n")
            .trimIndent()
}


private const val MAX_RESULT_PRINT_LINES = 10

fun List<String>.trimResults(maxLineCount: Int): List<String> {
    if (this.size < maxLineCount) return this
    return take(maxLineCount) + "... <${this.size - maxLineCount} more lines>" + "$size lines"
}


