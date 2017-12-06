import com.pascalwelsch.aoc.resourceFileText
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Ignore
import org.junit.Test

class Day6_LitLightsTest {

    // turn on 0,0 through 999,999 would turn on (or leave on) every light
    @Test
    fun `switch lights - all on`() {
        assertThat(litLights(listOf("turn on 0,0 through 999,999"), 1000))
                .isEqualTo(1000 * 1000)
    }

    // toggle 0,0 through 999,0 would toggle the first line of 1000 lights, turning off the ones
    // that were on, and turning on the ones that were off.
    @Test
    fun `switch lights - toggle`() {
        assertThat(litLights(listOf("toggle 0,0 through 999,0"), 1000))
                .isEqualTo(1000)


        val beforeToggle = litLights(listOf(
                "turn on 10,10 through 14,14",
                "turn on 100,0 through 149,0"
        ), 1000)
        assertThat(beforeToggle).isEqualTo(75)

        val afterToggle = litLights(listOf(
                "turn on 10,10 through 14,14",
                "turn on 100,0 through 149,0",
                "toggle 0,0 through 999,0"
        ), 1000)
        assertThat(afterToggle).isEqualTo((1000 - 50) + 25)
    }

    // turn off 499,499 through 500,500 would turn off (or leave off) the middle four lights.
    @Test
    fun `switch lights - toggle middle`() {
        val dontChange = litLights(listOf(
                "turn off 499,499 through 500,500"
        ), 1000)
        assertThat(dontChange).isEqualTo(0)

        val powerOffMiddle = litLights(listOf(
                "turn on 0,0 through 999,999",
                "turn off 499,499 through 500,500"
        ), 1000)
        assertThat(powerOffMiddle).isEqualTo(1000 * 1000 - 4)
    }

    @Test
    fun `switch lights - unknown command`() {
        assertThatThrownBy { litLights(listOf("something 499,499 through 500,500"), 100) }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessageContaining("unknown command")
    }

    @Ignore("slow test")
    @Test
    fun `switch lights - small`() {
        val input = resourceFileText("day6-1").lines()
        assertThat(litLights(input, 1000)).isEqualTo(569999)
    }

    @Test
    fun `grid implementation - all disabled by default`() {
        val grid = LightGrid(4)
        assertThat(grid.litCount()).isEqualTo(0)
    }

    @Test
    fun `grid implementation - lit all`() {
        val grid = LightGrid(4)
        grid.setRegion(GridRegion(0, 0, 3, 3)) { 1 }

        assertThat(grid.litCount()).isEqualTo(16)
    }

    @Test
    fun `grid implementation - toggle all`() {
        val grid = LightGrid(4)
        grid.setRegion(GridRegion(0, 0, 3, 3)) { 1 }
        assertThat(grid.litCount()).isEqualTo(16)

        grid.setRegion(GridRegion(0, 0, 3, 3)) { 0 }
        assertThat(grid.litCount()).isEqualTo(0)
    }

    // turn on 0,0 through 0,0 would increase the total brightness by 1.
    @Test
    fun `dimmable lights - increase total by on`() {
        assertThat(litLightsWithBrightness(listOf("turn on 0,0 through 0,0"), 1000))
                .isEqualTo(1)
    }

    // toggle 0,0 through 999,999 would increase the total brightness by 2000000.
    @Test
    fun `dimmable lights - increase every light`() {
        assertThat(litLightsWithBrightness(listOf("toggle 0,0 through 999,999"), 1000))
                .isEqualTo(2000000)
    }

    @Ignore("slow test")
    @Test
    fun `dimmable lights - part 2`() {
        val input = resourceFileText("day6-2").lines()
        assertThat(litLightsWithBrightness(input, 1000)).isEqualTo(17836115)
    }

}
