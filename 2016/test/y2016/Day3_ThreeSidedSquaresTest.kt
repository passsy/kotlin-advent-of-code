package y2016

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import y2016.Day3_ThreeSidedSquares.Triangle
import y2016.Day3_ThreeSidedSquares.part1
import y2016.Day3_ThreeSidedSquares.part2


class Day3_ThreeSidedSquaresTest {

    @Test
    fun `parse triangle`() {
        assertThat(Triangle.parse("  961  411  965")).isEqualTo(Triangle(961, 411, 965))
        assertThat(Triangle.parse("5\t10\t25")).isEqualTo(Triangle(5, 10, 25))
        assertThat(Triangle.parse("324 25 3")).isEqualTo(Triangle(324, 25, 3))
        assertThat(Triangle.parse("\t-2\t25\t3")).isEqualTo(Triangle(-2, 25, 3))
    }

    @Test
    fun `detect possible triangles`() {
        assertThat(Triangle(3, 4, 5).isPossible()).isTrue()
        assertThat(Triangle(4, 5, 3).isPossible()).isTrue()
        assertThat(Triangle(5, 3, 4).isPossible()).isTrue()
        assertThat(Triangle(5, 4, 3).isPossible()).isTrue()
        assertThat(Triangle(4, 3, 5).isPossible()).isTrue()
        assertThat(Triangle(3, 5, 4).isPossible()).isTrue()
    }

    @Test
    fun `detect impossible triangles`() {
        assertThat(Triangle(5, 10, 25).isPossible()).isFalse()
        assertThat(Triangle(10, 25, 5).isPossible()).isFalse()
        assertThat(Triangle(25, 5, 10).isPossible()).isFalse()
        assertThat(Triangle(25, 10, 5).isPossible()).isFalse()
        assertThat(Triangle(10, 5, 25).isPossible()).isFalse()
        assertThat(Triangle(5, 25, 10).isPossible()).isFalse()

        assertThat(Triangle(5, 5, 10).isPossible()).isFalse()
    }

    @Test
    fun `solve Part 1`() {
        assertThat(part1.test()).isEqualTo("993")
    }

    @Test
    fun `vertical triangle`() {
        val triangles = listOf(
                Triangle(101, 301, 501), Triangle(102, 302, 502), Triangle(103, 303, 503))
        assertThat(triangles.verticalTriangles()).isEqualTo(listOf(
                Triangle(101, 102, 103), Triangle(301, 302, 303), Triangle(501, 502, 503)))
    }

    @Test
    fun `solve Part 2`() {
        assertThat(part2.test()).isEqualTo("1849")
    }

}