package y2017

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import y2017.Day12_DigitalPlumber.buildMap
import y2017.Day12_DigitalPlumber.connectedPrograms
import y2017.Day12_DigitalPlumber.parseRelation
import y2017.Day12_DigitalPlumber.part1
import y2017.Day12_DigitalPlumber.part2


class Day12_DigitalPlumberTest {

    @Test
    fun `parse input`() {
        assertThat(parseRelation("0 <-> 2"))
                .isEqualTo(0 to listOf(2))

        assertThat(parseRelation("1 <-> 1"))
                .isEqualTo(1 to listOf(1))

        assertThat(parseRelation("2 <-> 0, 3, 4"))
                .isEqualTo(2 to listOf(0, 3, 4))

        assertThat(parseRelation("3 <-> 2, 4"))
                .isEqualTo(3 to listOf(2, 4))

        assertThat(parseRelation("4 <-> 2, 3, 6"))
                .isEqualTo(4 to listOf(2, 3, 6))

        assertThat(parseRelation("5 <-> 6"))
                .isEqualTo(5 to listOf(6))

        assertThat(parseRelation("6 <-> 4, 5"))
                .isEqualTo(6 to listOf(4, 5))
    }

    @Test
    fun `sample 1`() {
        val connections = listOf(
                0 to listOf(2),
                1 to listOf(1),
                2 to listOf(0, 3, 4),
                3 to listOf(2, 4),
                4 to listOf(2, 3, 6),
                5 to listOf(6),
                6 to listOf(4, 5)
        )
        assertThat(connectedPrograms(0, buildMap(connections))).isEqualTo(listOf(0, 2, 3, 4, 5, 6))
    }

    @Test
    fun `solve part one`() {
        assertThat(part1.test()).isEqualTo("283")
    }

    @Test
    fun `solve part two`() {
        assertThat(part2.test()).isEqualTo("195")
    }
}