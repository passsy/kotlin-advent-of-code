package y2017

import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test
import y2017.Day5_TwistyTrampolinesMaze.part1
import y2017.Day5_TwistyTrampolinesMaze.part2
import y2017.Day5_TwistyTrampolinesMaze.stepCount
import y2017.Day5_TwistyTrampolinesMaze.stepCount2


class Day5_TwistyTrampolinesMazeTest {

    @Test
    fun `small jump sample, exit at end`() {
        assertThat(stepCount(listOf(0, 3, 0, 1, -3))).isEqualTo(5)
    }

    @Test
    fun `exit at beginning`() {
        assertThat(stepCount(listOf(1, 2, 0, -6))).isEqualTo(3)
    }

    @Test
    fun `immediate exit`() {
        assertThat(stepCount(listOf(-1))).isEqualTo(1)
    }

    @Test
    fun `small stranger jump sample, exit at end`() {
        assertThat(stepCount2(listOf(0, 3, 0, 1, -3))).isEqualTo(10)
    }

    @Test
    fun `solve Part1`() {
        assertThat(part1.test()).isEqualTo("364539")
    }

    @Test
    @Ignore("slow test")
    fun `solve Part2`() {
        assertThat(part2.test()).isEqualTo("27477714")
    }
}