package y2017

import java.io.File
import java.io.StringWriter
import java.util.*


private const val MAX_RESULT_PRINT_LINES = 10

/**
 * Helper for small coding challenges. Reading file name from input and open output file buffer for the result output.
 *
 *
 * @param args main args, only reads the first argument as filename. absolute path or relative to `in/`. optional, asks for filename when not defined
 * @param block implementation solving the puzzle. input is the input file data by lines, output is the buffer to write the output to
 */
fun solve(name: String,
          input: String? = null,
          inputLines: List<String>? = null,
          inputFile: File? = null,
          outputFile: File? = null,
          block: (input: List<String>, outputBuilder: StringBuilder) -> Unit
): List<String> {
    val start = Date()
    println("Start solving '$name' at $start")
    val lines = input?.lines() ?: inputLines ?: inputFile?.let {
        println("\nreading file '${inputFile.absolutePath}'")
        if (!inputFile.exists()) {
            throw Exception("Can't load input file ${inputFile.absoluteFile}")
        }
        println("reading from ${inputFile.absoluteFile}")
        inputFile.readLines()
    } ?: throw Exception("no input provided")

    val writer = StringBuilder()
    block(lines, writer)
    val result = writer.toString()
    val resultLines = result.lines()

    outputFile?.let { output ->
        output.parentFile.mkdirs()
        val out = output.bufferedWriter()
        out.write(result)
        println("output in ${output.absoluteFile}")
        out.close()
        println("wrote ${resultLines.size} lines")
    }

    // print output partially because the can get very long
    println("\nResult of '$name':")
    resultLines.trimResults(MAX_RESULT_PRINT_LINES).forEach { println(it) }
    println("")

    val finish = Date()
    val diff = finish.time - start.time
    println("Solving '$name' took: ${diff / 1000.0}s")
    println("finished\n\n")
    return resultLines
}

fun List<String>.trimResults(maxLineCount: Int): List<String> {
    if (this.size < maxLineCount) return this
    return take(maxLineCount) + "... <${this.size - maxLineCount} more lines>"
}


