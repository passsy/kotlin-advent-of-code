package y2017

import org.assertj.core.api.Assertions.assertThat
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

        assertThat(followRoutingDiagram(graph)).isEqualTo("ABCDEF".toList())

    }
}