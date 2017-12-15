package y2017

import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test
import y2017.Day15_DuelingGenerators.judge
import y2017.Day15_DuelingGenerators.part1
import y2017.Day15_DuelingGenerators.part2

class Day15_DuelingGeneratorsTest {

    val a = Day15_DuelingGenerators.Generator("sample A", 65).apply { factor = 16807 }
    val b = Day15_DuelingGenerators.Generator("sample A", 8921).apply { factor = 48271 }
    @Test
    fun `sample A generator values`() {
        assertThat(a.take(5).toList())
                .isEqualTo(listOf(1092455L, 1181022009L, 245556042L, 1744312007L, 1352636452L))
    }

    @Test
    fun `sample B generator values`() {
        assertThat(b.take(5).toList())
                .isEqualTo(listOf(430625591L, 1233683848L, 1431495498L, 137874439L, 285222916L))
    }

    @Test
    fun `judge sample`() {
        assertThat(judge(a, b, 5)).isEqualTo(1)
    }

    @Ignore("executes too long")
    @Test
    fun `solve part one`() {
        assertThat(part1.test()).isEqualTo("609")
    }

    @Test
    fun `solve part one simplified`() {
        assertThat(judge(a, b, 50_000)).isEqualTo(3)
    }

    @Ignore("executes too long")
    @Test
    fun `solve part two`() {
        assertThat(part2.test()).isEqualTo("253")
    }

    @Test
    fun `solve part two simplified`() {
        assertThat(judge(a.filter { it % 4L == 0L }, b.filter { it % 8L == 0L }, 9_000))
                .isEqualTo(2)
    }

}