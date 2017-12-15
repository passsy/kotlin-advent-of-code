package y2017

import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test
import y2017.Day10_KnotHash.createInputSequence
import y2017.Day10_KnotHash.denseHash
import y2017.Day10_KnotHash.knot
import y2017.Day10_KnotHash.knotHash
import y2017.Day10_KnotHash.knotHashSequence
import y2017.Day10_KnotHash.part1
import y2017.Day10_KnotHash.part2

class Day10_KnotHashTest {

    @Test
    fun `hash reverse all items`() {
        assertThat(listOf(0, 1, 2).knot(start = 0, length = 3))
                .isEqualTo(listOf(2, 1, 0))
    }

    @Test
    fun `hash reverse middle`() {
        assertThat(listOf(0, 1, 2, 3, 4).knot(start = 1, length = 3))
                .isEqualTo(listOf(0, 3, 2, 1, 4))
    }

    @Test
    fun `simple hash without overlap`() {
        assertThat(listOf(0, 1, 2, 3, 4).knot(start = 0, length = 3))
                .isEqualTo(listOf(2, 1, 0, 3, 4))
    }

    @Test
    fun `simple hash with overlap`() {
        assertThat(listOf(2, 1, 0, 3, 4).knot(start = 3, length = 4))
                .isEqualTo(listOf(4, 3, 0, 1, 2))
    }

    @Test
    fun `do nothing for length 1`() {
        assertThat(listOf(4, 3, 0, 1, 2).knot(start = 1, length = 1))
                .isEqualTo(listOf(4, 3, 0, 1, 2))
    }

    @Test
    fun `select every element`() {
        assertThat(listOf(4, 3, 0, 1, 2).knot(start = 1, length = 5))
                .isEqualTo(listOf(3, 4, 2, 1, 0))
    }

    @Test
    fun `knothash sample`() {
        assertThat(knotHashSequence(listOf(3, 4, 1, 5), listSize = 4, rounds = 1))
                .isEqualTo(listOf(3, 4, 2, 1, 0))
    }

    @Test
    fun `solve part one`() {
        assertThat(part1.test()).isEqualTo("15990")
    }

    @Test
    fun `parse sample input part 2`() {
        assertThat(createInputSequence("1,2,3")).isEqualTo(
                listOf(49, 44, 50, 44, 51, 17, 31, 73, 47, 23))
    }

    @Test
    fun `dense hash`() {
        val sparseHash = listOf(65, 27, 9, 1, 4, 3, 40, 50, 91, 7, 6, 0, 2, 5, 68, 22)
        assertThat(denseHash(sparseHash)).isEqualTo(listOf(64))
    }

    @Test
    fun `knot hash sample 1`() {
        assertThat(knotHash("")).isEqualTo("a2582a3a0e66e6e86e3812dcb672a272")
    }

    @Test
    fun `knot hash sample 2`() {
        assertThat(knotHash("AoC 2017")).isEqualTo("33efeb34ea91902bb2f59c9920caa6cd")
    }

    @Test
    fun `knot hash sample 3`() {
        assertThat(knotHash("1,2,3")).isEqualTo("3efbe78a8d82f29979031a4aa0b16a9d")
    }

    @Test
    fun `knot hash sample 4`() {
        assertThat(knotHash("1,2,4")).isEqualTo("63960835bcdc130f0b66d7ff4f6a5a8e")
    }

    @Ignore("executes too long")
    @Test
    fun `solve part two`() {
        assertThat(part2.test()).isEqualTo("90adb097dd55dea8305c900372258ac6")
    }
}