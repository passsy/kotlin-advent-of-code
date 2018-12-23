package y2017

fun main() {

    printTree(10)
}

fun printTree(height: Int) {

    // Print Tree
    for (row in IntRange(0, height)) {
        for (j in IntRange(0, height - row)) {
            print(" ")
        }
        for (j in IntRange(0, row * 2)) {
            print("*")
        }
        println()
    }
    // Print Trunk
    for (row in IntRange(0, 3)) {
        for (j in IntRange(0, height - 2)) {
            print(" ")
        }
        for (j in IntRange(0, 3)) {
            print("x")
        }
        println()
    }
}