package y2017

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import y2017.Day11_HexEd.HexDirection
import y2017.Day11_HexEd.HexDirection.*
import y2017.Day11_HexEd.HexPosition
import y2017.Day11_HexEd.followDirections
import y2017.Day11_HexEd.furthestDistance
import y2017.Day11_HexEd.part1
import y2017.Day11_HexEd.part2


class Day11_HexEdTest {

    private val origin = HexPosition(0, 0)

    @Test
    fun `parse directions`() {
        assertThat(listOf("n", "ne", "se", "s", "sw", "nw").map { HexDirection.from(it) })
                .isEqualTo(listOf(NORTH, NORTH_EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, NORTH_WEST))
    }

    @Test
    fun `three times north east`() {
        val directions = listOf(NORTH_EAST, NORTH_EAST, NORTH_EAST)
        assertThat(followDirections(directions, origin).distance(origin)).isEqualTo(3)
    }

    @Test
    fun `back to where started`() {
        val directions = listOf(NORTH_EAST, NORTH_EAST, SOUTH_WEST, SOUTH_WEST)
        assertThat(followDirections(directions, origin).distance(origin)).isEqualTo(0)
    }

    @Test
    fun `two directions`() {
        val directions = listOf(NORTH_EAST, NORTH_EAST, SOUTH, SOUTH)
        assertThat(followDirections(directions, origin).distance(origin)).isEqualTo(2)
    }

    @Test
    fun `zigzag south`() {
        val directions = listOf(SOUTH_EAST, SOUTH_WEST, SOUTH_EAST, SOUTH_WEST, SOUTH_WEST)
        assertThat(followDirections(directions, origin).distance(origin)).isEqualTo(3)
    }

    @Test
    fun `solve part one`() {
        assertThat(part1.test()).isEqualTo("796")
    }

    @Test
    fun `distance back to where started`() {
        val directions = listOf(NORTH_EAST, NORTH_EAST, SOUTH_WEST, SOUTH_WEST)
        assertThat(furthestDistance(directions, origin)).isEqualTo(2)
    }

    @Test
    fun `solve part two`() {
        assertThat(part2.test()).isEqualTo("1585")
    }
}