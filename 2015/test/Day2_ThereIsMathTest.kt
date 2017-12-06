import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class Day2_ThereIsMathTest {

    @Test
    fun `A present with dimensions 2x3x4 requires 58 square feet`() {
        assertThat(requiredPaper("2x3x4")).isEqualTo(58)
    }

    @Test
    fun `A present with dimensions 1x1x10 requires 43 square feet`() {
        assertThat(requiredPaper("1x1x10")).isEqualTo(43)
    }

    @Test
    fun `combined paper required`() {
        val input = file("day2-1").readLines()
        val result = input.map { requiredPaper(it) }.sum()
        assertThat(result).isEqualTo(1598415)
    }

    @Test
    fun `calculate ribbon - A present with dimensions 2x3x4 34 feet`() {
        assertThat(requiredRibbon("2x3x4")).isEqualTo(34)
    }

    @Test
    fun `calculate ribbon - A present with dimensions 1x1x10 requires 14 feet`() {
        assertThat(requiredRibbon("1x1x10")).isEqualTo(14)
    }

    @Test
    fun `combined ribbon required`() {
        val input = file("day2-2").readLines()
        val result = input.map { requiredRibbon(it) }.sum()
        assertThat(result).isEqualTo(3812909)
    }
}