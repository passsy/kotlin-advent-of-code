package y2017

import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test
import y2017.Day19_SeriesOfTubes.followRoutingDiagram


class Day19_SeriesOfTubesTest {

    @Test
    fun `sample 1`() {
        val graph = """
            |     |
            |     |  +--+
            |     A  |  C
            | F---|----E|--+
            |     |  |  |  D
            |     +B-+  +--+
            """.replaceIndentByMargin().drop(0).lines().map { it.toList() }

        assertThat(followRoutingDiagram(graph).letters).isEqualTo("ABCDEF")
    }

    @Ignore("too large (115ms)")
    @Test
    fun `solve part 1`() {
        assertThat(Day19_SeriesOfTubes.part1.test()).isEqualTo("MKXOIHZNBL")
    }

    @Test
    fun `sample 2`() {
        val graph = """
            |     |
            |     |  +--+
            |     A  |  C
            | F---|----E|--+
            |     |  |  |  D
            |     +B-+  +--+
            """.replaceIndentByMargin().drop(0).lines().map { it.toList() }

        assertThat(followRoutingDiagram(graph).steps).isEqualTo(38)
    }


    @Test
    fun `solve part 2`() {
        assertThat(Day19_SeriesOfTubes.part2.test()).isEqualTo("17872")
    }

}