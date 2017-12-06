package y2016

import com.pascalwelsch.aoc.resourceFileText
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import y2016.Day2_BathroomSecurity.buttons
import y2016.Day2_BathroomSecurity.pad9
import y2016.Day2_BathroomSecurity.part1
import y2016.Day2_BathroomSecurity.part2


class Day2_BathroomSecurityTest {

    // You start at "5" and move up (to "2"), left (to "1"), and left (you can't, and stay on "1"),
    // so the first button is 1.
    @Test
    fun `inital start at 5 - move to 1`() {
        val buttons = buttons(listOf("ULL".toList()), pad9)
        assertThat(buttons).isEqualTo(listOf("1"))
    }

    @Test
    fun `sample 1`() {
        val instructions = resourceFileText("2016/2-sample.txt").lines().map { it.toList() }
        assertThat(buttons(instructions, pad9)).isEqualTo(listOf("1", "9", "8", "5"))
    }

    @Test
    fun `solve Part 1`() {
        assertThat(part1.test()).isEqualTo("74921")
    }

    @Test
    fun `solve Part 2`() {
        assertThat(part2.test()).isEqualTo("A6B35")
    }
}