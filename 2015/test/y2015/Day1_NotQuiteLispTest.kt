package y2015

import com.pascalwelsch.aoc.resourceFileText
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Ignore
import org.junit.Test

class Day1_NotQuiteLispTest {

    @Test
    fun `Santas final position - (()) and ()() both result in floor 0`() {
        assertThat(whatFloor("(())")).isEqualTo(0)
        assertThat(whatFloor("()()")).isEqualTo(0)
    }

    @Test
    fun `Santas final position - ((( and (()(()( both result in floor 3`() {
        assertThat(whatFloor("(((")).isEqualTo(3)
        assertThat(whatFloor("(()(()(")).isEqualTo(3)
    }

    @Test
    fun `Santas final position - ))((((( also results in floor 3`() {
        assertThat(whatFloor("))(((((")).isEqualTo(3)
    }

    @Test
    fun `Santas final position - ()) and ))( both result in floor -1 (the first basement level)`() {
        assertThat(whatFloor("())")).isEqualTo(-1)
        assertThat(whatFloor("))(")).isEqualTo(-1)

    }

    @Test
    fun `Santas final position - ))) and )())()) both result in floor -3`() {
        assertThat(whatFloor(")))")).isEqualTo(-3)
        assertThat(whatFloor(")())())")).isEqualTo(-3)
    }

    @Ignore("slow test")
    @Test
    fun `solve part1`() {
        val input = resourceFileText("2015/day1-1")
        assertThat(whatFloor(input)).isEqualTo(138)
    }

    @Test
    fun `When reaches Santa floor -1 - ) causes him to enter the basement at character position 1`() {
        assertThat(whenEnterBasement(")")).isEqualTo(1)
    }

    @Test
    fun `When reaches Santa floor -1 - ()()) causes him to enter the basement at character position 5`() {
        assertThat(whenEnterBasement("()())")).isEqualTo(5)
    }

    @Test
    fun `When reaches Santa floor -1 - crash when never reaching the basement`() {
        assertThatThrownBy { whenEnterBasement("(") }
                .isInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun `solve part2`() {
        val input = resourceFileText("2015/day1-2")
        assertThat(whenEnterBasement(input)).isEqualTo(1771)
    }

}