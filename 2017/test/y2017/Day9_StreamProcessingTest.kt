package y2017

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import y2017.Day9_StreamProcessing.parse
import y2017.Day9_StreamProcessing.part1
import y2017.Day9_StreamProcessing.part2

class Day9_StreamProcessingTest {
    @Test
    fun `one simple group`() {
        val result = parse("{}")
        assertThat(result.groups).isEqualTo(1)
        assertThat(result.groupScore).isEqualTo(1)
    }

    @Test
    fun `nested groups`() {
        val result = parse("{{{}}}")
        assertThat(result.groups).isEqualTo(3)
        assertThat(result.groupScore).isEqualTo(6)
    }

    @Test
    fun `group siblings`() {
        val result = parse("{{},{}}")
        assertThat(result.groups).isEqualTo(3)
        assertThat(result.groupScore).isEqualTo(5)
    }

    @Test
    fun `siblings which have nested children`() {
        val result = parse("{{{},{},{{}}}}")
        assertThat(result.groups).isEqualTo(6)
        assertThat(result.groupScore).isEqualTo(16)
    }

    @Test
    fun `ignore garbage`() {
        val result = parse("{<a>,<a>,<a>,<a>}")
        assertThat(result.groups).isEqualTo(1)
        assertThat(result.groupScore).isEqualTo(1)
    }

    @Test
    fun `ignore garbage in nested groups`() {
        val result = parse("{{<ab>},{<ab>},{<ab>},{<ab>}}")
        assertThat(result.groups).isEqualTo(5)
        assertThat(result.groupScore).isEqualTo(9)
    }

    @Test
    fun `cancel the cancel char`() {
        val result = parse("{{<!!>},{<!!>},{<!!>},{<!!>}}")
        assertThat(result.groups).isEqualTo(5)
        assertThat(result.groupScore).isEqualTo(9)
    }

    @Test
    fun `cancel the garbage end`() {
        val result = parse("{{<a!>},{<a!>},{<a!>},{<ab>}}")
        assertThat(result.groups).isEqualTo(2)
        assertThat(result.groupScore).isEqualTo(3)
    }

    @Test
    fun `ignore garbage start and end chars`() {
        assertThat(parse("<>").garbageCount).isEqualTo(0)
    }

    @Test
    fun `simple garbage calculation`() {
        assertThat(parse("<random characters>").garbageCount).isEqualTo(17)
    }

    @Test
    fun `opening garbage chars inside garbage is garbage`() {
        assertThat(parse("<<<<>").garbageCount).isEqualTo(3)
    }

    @Test
    fun `ignore garbage close`() {
        assertThat(parse("<{!>}>").garbageCount).isEqualTo(2)
    }

    @Test
    fun `canceling characters are no garbage`() {
        assertThat(parse("<!!>").garbageCount).isEqualTo(0)
    }

    @Test
    fun `canceling closing garbage char`() {
        assertThat(parse("<!!!>>").garbageCount).isEqualTo(0)
    }

    @Test
    fun `any char in garbage is allowed and gets counted`() {
        assertThat(parse("<{o\"i!a,<{i<a>").garbageCount).isEqualTo(10)
    }

    @Test
    fun `solve part one`() {
        assertThat(part1.test()).isEqualTo("17537")
    }

    @Test
    fun `solve part two`() {
        assertThat(part2.test()).isEqualTo("7539")
    }
}
