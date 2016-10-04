package common

import java.io.BufferedWriter
import java.io.File
import java.util.*


/**
 * Helper for small coding challenges. Reading file name from input and open output file buffer for the result output.
 *
 *
 * @param args main args, only reads the first argument as filename. absolute path or relative to `in/`. optional, asks for filename when not defined
 * @param block implementation solving the puzzle. input is the input file data by lines, output is the buffer to write the output to
 */
fun solveFromInput(vararg args: String = arrayOf(),
                   block: (input: List<String>, output: BufferedWriter) -> Unit) {
    val start = Date()
    val filename: String = args.getOrElse(0, {
        println("enter the name of the input file in folder `in/` or the absolute path:")
        readLine()!!
    })
    println("\nsolving '$filename'")
    println("starting at $start")
    var inFile = File(filename)
    if (!inFile.exists()) {
        inFile = File("in/$filename")
    }
    if (!inFile.exists()) {
        throw Exception("Can't load input file ${File(filename).absoluteFile}")
    }
    println("reading from ${inFile.absoluteFile}")
    val input = inFile.readLines()

    val output = File("solutions/out_${inFile.nameWithoutExtension}")
    output.parentFile.mkdirs()
    val out = output.bufferedWriter()

    block(input, out)

    println("output in ${output.absoluteFile}")
    out.close()
    println("wrote ${output.readLines().size} lines")
    val finish = Date()
    val diff = finish.time - start.time
    println("Duration: ${diff / 1000.0}s\nfinished")
}

fun main(args: Array<String>) {
    solveFromInput(*args) { input, output ->
        // write input in output
        input.forEach { output.write(it) }
    }
}