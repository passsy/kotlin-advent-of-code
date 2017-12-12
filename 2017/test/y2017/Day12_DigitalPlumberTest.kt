package y2017

import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test
import y2017.Day12_DigitalPlumber.buildMap
import y2017.Day12_DigitalPlumber.connectedProgramCount
import y2017.Day12_DigitalPlumber.groupCount
import y2017.Day12_DigitalPlumber.parseRelation
import y2017.Day12_DigitalPlumber.part1
import y2017.Day12_DigitalPlumber.part2


class Day12_DigitalPlumberTest {

    @Test
    fun `parse input`() {
        assertThat(parseRelation("0 <-> 2"))
                .isEqualTo(setOf(0, 2))

        assertThat(parseRelation("1 <-> 1"))
                .isEqualTo(setOf(1))

        assertThat(parseRelation("2 <-> 0, 3, 4"))
                .isEqualTo(setOf(2, 0, 3, 4))

        assertThat(parseRelation("3 <-> 2, 4"))
                .isEqualTo(setOf(3, 2, 4))

        assertThat(parseRelation("4 <-> 2, 3, 6"))
                .isEqualTo(setOf(4, 2, 3, 6))

        assertThat(parseRelation("5 <-> 6"))
                .isEqualTo(setOf(5, 6))

        assertThat(parseRelation("6 <-> 4, 5"))
                .isEqualTo(setOf(6, 4, 5))
    }

    @Test
    fun `sample 1`() {
        val map = buildMap(listOf(
                setOf(0, 2),
                setOf(1),
                setOf(2, 0, 3, 4),
                setOf(3, 2, 4),
                setOf(4, 2, 3, 6),
                setOf(5, 6),
                setOf(6, 4, 5)
        ))
        assertThat(connectedProgramCount(0, map)).isEqualTo(6)
    }

    @Ignore("too slow")
    @Test
    fun `solve part one`() {
        assertThat(part1.test()).isEqualTo("283")
    }

    @Test
    fun `sample 2`() {
        val map = buildMap(listOf(
                setOf(0, 2),
                setOf(1),
                setOf(2, 0, 3, 4),
                setOf(3, 2, 4),
                setOf(4, 2, 3, 6),
                setOf(5, 6),
                setOf(6, 4, 5)
        ))
        assertThat(groupCount(map)).isEqualTo(2)
    }


    @Ignore("too slow")
    @Test
    fun `solve part two`() {
        assertThat(part2.test()).isEqualTo("195")
    }
}