package y2017

import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test
import y2017.Day16_PermutationPromenade.Command.*
import y2017.Day16_PermutationPromenade.dance
import y2017.Day16_PermutationPromenade.parseCommand
import y2017.Day16_PermutationPromenade.part1
import y2017.Day16_PermutationPromenade.part2


class Day16_PermutationPromenadeTest {
    @Test
    fun `sample 1 dance`() {
        val commands = listOf("s1", "x3/4", "pe/b").map { parseCommand(it) }

        assertThat(dance(commands, initialOrder = listOf("a", "b", "c", "d", "e")))
                .isEqualTo(listOf("b", "a", "e", "d", "c"))
    }

    @Test
    fun `swap`() {
        assertThat(dance(listOf(Spin(2)), initialOrder = listOf("a", "b", "c", "d", "e")))
                .isEqualTo(listOf("d", "e", "a", "b", "c"))
    }

    @Test
    fun `exchange`() {
        assertThat(dance(listOf(Exchange(1, 4)), initialOrder = listOf("a", "b", "c", "d", "e")))
                .isEqualTo(listOf("a", "e", "c", "d", "b"))
    }

    @Test
    fun `partner`() {
        assertThat(dance(listOf(Partner("b", "e")), initialOrder = listOf("a", "b", "c", "d", "e")))
                .isEqualTo(listOf("a", "e", "c", "d", "b"))
    }

    @Ignore("takes 200ms")
    @Test
    fun `solve part 1`() {
        assertThat(part1.test()).isEqualTo("ceijbfoamgkdnlph")
    }

    @Ignore("takes 600ms")
    @Test
    fun `solve part 2`() {
        assertThat(part2.test()).isEqualTo("pnhajoekigcbflmd")
    }
}