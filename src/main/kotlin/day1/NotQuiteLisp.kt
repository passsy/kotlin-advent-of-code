package day1

import java.io.File
import java.util.*

fun main(args: Array<String>) {
    val start = Date()
    println("starting $start")
    val filename: String = args.getOrElse(0, { readLine()!! })
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

    val result = whatFloor(input[0])
    out.write("$result")

    println("write in output file ${output.absoluteFile}")
    out.close()
    println("wrote ${output.readLines().size} lines")
    val finish = Date()
    val diff = finish.time - start.time
    println("\nDuration: ${diff / 1000.0}s\nfinished")
}

fun whatFloor(input: String): Int {
    val chars = input.toCharArray()
    val down = chars.filter { it == ')' }.count()
    val up = chars.filter { it == '(' }.count()
    return up - down
}
