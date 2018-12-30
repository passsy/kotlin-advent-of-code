package teresa

fun main() {

    println("FizzBuzz")

    // var i = 0
    // while (i < 100) {
    //    i = i + 1
    //     println(i)
    // }

    for (row in IntRange(1, 100)) {

        if (row % 3 == 0 && row % 5 == 0) {
            println("FizzBuzz")
        } else if (row % 3 == 0) {
            println("Fizz")
        } else if (row % 5 == 0) {
            println("Buzz")
        } else {
            println(row)
        }
    }
}


