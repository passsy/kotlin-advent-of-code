fun main(args: Array<String>) {
    solveFromInput("day3-1") { input, output ->
        val result = numberOfVisitedHouses(input[0])
        output.write("$result")
    }

    solveFromInput("day3-2") { input, output ->
        val result = numberOfVisitedHouses(input[0], 2)
        output.write("$result")
    }
}

/**
Santa is delivering presents to an infinite two-dimensional grid of houses.

He begins by delivering a present to the house at his starting location, and then an elf at the North Pole calls him via radio and tells him where to move next. Moves are always exactly one house to the north (^), south (v), east (>), or west (<). After each move, he delivers another present to the house at his new location.

However, the elf back at the north pole has had a little too much eggnog, and so his directions are a little off, and Santa ends up visiting some houses more than once. How many houses receive at least one present?

For example:

> delivers presents to 2 houses: one at the starting location, and one to the east.
^>v< delivers presents to 4 houses in a square, including twice to the house at his starting/ending location.
^v^v^v^v^v delivers a bunch of presents to some very lucky children at only 2 houses.
 */
fun numberOfVisitedHouses(input: String): Int {
    return numberOfVisitedHouses(input, 1)
}

data class House(
        val x: Int,
        val y: Int
)

/**
The next year, to speed up the process, Santa creates a robot version of himself, Robo-Santa, to deliver presents with him.

Santa and Robo-Santa start at the same location (delivering two presents to the same starting house), then take turns moving based on instructions from the elf, who is eggnoggedly reading from the same script as the previous year.

This year, how many houses receive at least one present?

For example:

^v delivers presents to 3 houses, because Santa goes north, and then Robo-Santa goes south.
^>v< now delivers presents to 3 houses, and Santa and Robo-Santa end up back where they started.
^v^v^v^v^v now delivers presents to 11 houses, with Santa going one direction and Robo-Santa going the other.
 */
fun numberOfVisitedHouses(input: String, santaCount: Int): Int {
    require(santaCount > 0) { "at least one santa required" }
    val chars = input.toCharArray()

    // initialize
    val start = House(0, 0)
    val santaPositions = mutableMapOf<Int, House>()
    for (i in 0..(santaCount - 1)) {
        santaPositions[i] = start
    }

    val visitedHouses = mutableSetOf<House>()
    visitedHouses.add(start)

    chars.forEachIndexed { i, it ->
        val santaId = i % santaCount
        var currentHouse = santaPositions[santaId]!!

        currentHouse = when (it) {
            '<' -> currentHouse.copy(x = currentHouse.x - 1)
            '>' -> currentHouse.copy(x = currentHouse.x + 1)
            '^' -> currentHouse.copy(y = currentHouse.y - 1)
            'v' -> currentHouse.copy(y = currentHouse.y + 1)
            else -> throw IllegalStateException("unexpected character '$it'")

        }
        visitedHouses.add(currentHouse)
        santaPositions[santaId] = currentHouse
    }
    return visitedHouses.size
}