package y2017

import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test
import y2017.Day6_MemoryReallocation.part1
import y2017.Day6_MemoryReallocation.part2
import y2017.Day6_MemoryReallocation.recursionLoopSize
import y2017.Day6_MemoryReallocation.redistribute
import y2017.Day6_MemoryReallocation.stepsUntilRecursion

class Day6_MemoryReallocationTest {

    @Test
    fun `redistribute returns a new list instance`() {
        val input = listOf(1, 2, 3)
        val output = redistribute(input)
        assertThat(input).isNotSameAs(output)
    }

    @Test
    fun `redistribute - sample 0`() {
        val redistributed = redistribute(listOf(0, 2, 7, 0))
        assertThat(redistributed).isEqualTo(listOf(2, 4, 1, 2))
    }

    @Test
    fun `redistribute - sample 1`() {
        val redistributed = redistribute(listOf(2, 4, 1, 2))
        assertThat(redistributed).isEqualTo(listOf(3, 1, 2, 3))
    }

    @Test
    fun `redistribute - sample 2`() {
        val redistributed = redistribute(listOf(3, 1, 2, 3))
        assertThat(redistributed).isEqualTo(listOf(0, 2, 3, 4))
    }

    @Test
    fun `detect recursion - sample`() {
        val steps = stepsUntilRecursion(listOf(0, 2, 7, 0))
        assertThat(steps).isEqualTo(5)
    }

    @Test
    fun `recursion loop size - sample`() {
        val steps = recursionLoopSize(listOf(0, 2, 7, 0))
        assertThat(steps).isEqualTo(4)
    }

    @Test
    @Ignore("slow test")
    fun `solve Part1`() {
        assertThat(part1.test()).isEqualTo("12841")
    }

    @Test
    @Ignore("slow test")
    fun `solve Part2`() {
        assertThat(part2.test()).isEqualTo("8038")
    }
}