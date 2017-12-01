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
        block: (input: List<String>, output: StringWriter) -> Unit
): List<String> {
    val start = Date()
    println("starting solving $name at $start")
    val inputLines = input?.lines() ?: inputLines ?: inputFile?.let {
        val filename = inputFile.name
        println("\nsolving '$inputFile'")
        var inFile = inputFile
        if (!inFile.exists()) {
            throw Exception("Can't load input file ${File(filename).absoluteFile}")
        }
        println("reading from ${inFile.absoluteFile}")
        inFile.readLines()
    } ?: throw Exception("no input provided")


    val writer = StringWriter()
    block(inputLines, writer)
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

    // print output partially
    println("\nResult of $name:")
    for (i in 0 until resultLines.size) {
        if (i > MAX_RESULT_PRINT_LINES) {
            println("... <${resultLines.size - MAX_RESULT_PRINT_LINES} more lines>")
            break
        }
        println(resultLines[i])
    }
    println("")

    val finish = Date()
    val diff = finish.time - start.time
    println("$name Duration: ${diff / 1000.0}s\nfinished")
    return resultLines
}