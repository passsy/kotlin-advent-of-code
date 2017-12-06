package com.pascalwelsch.aoc

import java.io.File
import java.io.FileNotFoundException


fun fileText(path: String, trim: Boolean = true) : String {
    return fileText(File(path), trim)
}

fun resourceFileText(path: String, trim: Boolean = true): String {
    val resource = Int::class.java.getResource(path)
    if (resource == null) throw FileNotFoundException("could not load '$path'")
    val file = File(resource.toURI())
    return fileText(file)
}


fun fileText(file: File, trim: Boolean = true) : String {
    println("reading input file '${file.absolutePath}'")
    if (!file.exists()) {
        throw FileNotFoundException(
                "couldn't find input file '${file.path}' at ${file.absolutePath}")
    }
    val text = file.readText()
    if (text.isEmpty()) throw IllegalArgumentException("Input file is empty")
    println("input file '${file.path}' has ${text.lines().size} lines")
    val trimmed = if (trim) {
        text.trimChallengeInput()
    } else {
        text
    }
    return trimmed
}

/**
 * trim the text matching typical challenge inputs, remove empty lines at start and bottom and removes indention.
 */
internal fun String.trimChallengeInput(): String {
    return lines()
            .dropWhile { it.isBlank() }
            .dropLastWhile { it.isBlank() }
            .joinToString("\n")
            .trimIndent()
}