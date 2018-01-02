package y2017

import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test
import y2017.Day22_SporificaVirus.moveAdvanced
import y2017.Day22_SporificaVirus.moveSimple
import y2017.Day22_SporificaVirus.part1
import y2017.Day22_SporificaVirus.part2


class Day22_SporificaVirusTest {

    @Test
    fun `sample 1 - 70`() {
        val infections = moveSimple(70, listOf("..#", "#..", "...").map { it.toList() })
        assertThat(infections).isEqualTo(41)
    }

    @Test
    fun `sample 1 - 10k`() {
        val infections = moveSimple(10_000, listOf("..#", "#..", "...").map { it.toList() })
        assertThat(infections).isEqualTo(5587)
    }

    @Test
    fun `sample 1, one step`() {
        val infections = moveSimple(1, listOf("..#", "#..", "...").map { it.toList() })
        assertThat(infections).isEqualTo(1)
    }

    @Test
    fun `sample 1, after 7`() {
        val infections = moveSimple(7, listOf("..#", "#..", "...").map { it.toList() })
        assertThat(infections).isEqualTo(5)
    }

    @Test
    fun `solve part 1`() {
        assertThat(part1.test()).isEqualTo("5246")
    }

    @Test
    fun `sample 2, after 100`() {
        val infections = moveAdvanced(100, listOf("..#", "#..", "...").map { it.toList() })
        assertThat(infections).isEqualTo(26)
    }

    @Ignore("too large (115ms)")
    @Test
    fun `solve part 2`() {
        assertThat(part2.test()).isEqualTo("2512059")
    }
}