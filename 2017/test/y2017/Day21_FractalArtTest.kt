package y2017

import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test
import y2017.Day21_FractalArt.Pattern
import y2017.Day21_FractalArt.combine
import y2017.Day21_FractalArt.flip
import y2017.Day21_FractalArt.part1
import y2017.Day21_FractalArt.part2
import y2017.Day21_FractalArt.rotate90
import y2017.Day21_FractalArt.tile
import y2017.Day21_FractalArt.toArt


class Day21_FractalArtTest {

    @Test
    fun `flip horizontally`() {
        val grid = listOf(
                listOf('a', 'b'),
                listOf('a', 'b')).flip()

        assertThat(grid).isEqualTo(listOf(
                listOf('b', 'a'),
                listOf('b', 'a')))
    }

    @Test
    fun `rotate 90 - 2x2`() {
        val grid = listOf(
                listOf('a', 'b'),
                listOf('a', 'b')).rotate90()

        assertThat(grid).isEqualTo(listOf(
                listOf('a', 'a'),
                listOf('b', 'b')))
    }

    @Test
    fun `rotate 90 - 3x3`() {
        val grid = listOf(
                listOf('a', 'b', 'c'),
                listOf('d', 'e', 'f'),
                listOf('g', 'h', 'i')).rotate90()

        assertThat(grid).isEqualTo(listOf(
                listOf('g', 'd', 'a'),
                listOf('h', 'e', 'b'),
                listOf('i', 'f', 'c')))
    }

    @Test
    fun `pattern matches all variants`() {
        val pattern = Pattern(".#./..#/###".toArt(), emptyList())
        assertThat(pattern.variants).contains(
                ".#./..#/###".toArt(), // original
                "#../#.#/##.".toArt(), // rotate 90
                "###/#../.#.".toArt(), // rotate 180
                ".##/#.#/..#".toArt(), // rotate 270

                ".#./#../###".toArt(), // original flip
                "..#/#.#/.##".toArt(), // rotate 90 flip
                "###/..#/.#.".toArt(), // rotate 180 flip
                "##./#.#/#..".toArt() // rotate 270 flip
        )
    }

    @Test
    fun `tile art`() {

        val art = listOf(
                listOf('a', 'b', 'c', '1'),
                listOf('d', 'e', 'f', '2'),
                listOf('g', 'h', 'i', '3'),
                listOf('4', '5', '6', '7'))

        val split = art.tile()
        assertThat(split).isEqualTo(listOf(
                listOf(
                        listOf('a', 'b'),
                        listOf('d', 'e')),
                listOf(
                        listOf('c', '1'),
                        listOf('f', '2')),
                listOf(
                        listOf('g', 'h'),
                        listOf('4', '5')),
                listOf(
                        listOf('i', '3'),
                        listOf('6', '7'))))
    }

    @Test
    fun `combine art`() {
        val split = listOf(
                listOf(
                        listOf('a', 'b'),
                        listOf('d', 'e')),
                listOf(
                        listOf('c', '1'),
                        listOf('f', '2')),
                listOf(
                        listOf('g', 'h'),
                        listOf('4', '5')),
                listOf(
                        listOf('i', '3'),
                        listOf('6', '7')))
        val combined = split.combine()
        assertThat(combined).isEqualTo(listOf(
                listOf('a', 'b', 'c', '1'),
                listOf('d', 'e', 'f', '2'),
                listOf('g', 'h', 'i', '3'),
                listOf('4', '5', '6', '7'))
        )
    }

    @Test
    fun `solve part 1`() {
        assertThat(part1.test()).isEqualTo(110)
    }

    @Ignore("too large")
    @Test
    fun `solve part 2`() {
        assertThat(part2.test()).isEqualTo(1277716)
    }
}