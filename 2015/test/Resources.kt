import java.io.File
import java.io.FileNotFoundException

fun file(path: String): File {
    val file = Int::class.java.getResource("day1-1")?.let { File(it.toURI()) }
    return file ?: throw FileNotFoundException("could not load file $path")
}
