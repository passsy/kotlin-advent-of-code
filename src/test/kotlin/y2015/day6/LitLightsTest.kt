package y2015.day6

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.io.File

@RunWith(JUnitPlatform::class)
class LitLightsTest : Spek({

    describe("switch lights according to santas instructions") {

        test("turn on 0,0 through 999,999 would turn on (or leave on) every light.") {
            assertThat(litLights(listOf("turn on 0,0 through 999,999"), 1000))
                    .isEqualTo(1000 * 1000)
        }
        test("toggle 0,0 through 999,0 would toggle the first line of 1000 lights, turning off the ones that were on, and turning on the ones that were off.") {
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
        test("turn off 499,499 through 500,500 would turn off (or leave off) the middle four lights.") {
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

        test("unknown command") {
            assertThatThrownBy { litLights(listOf("something 499,499 through 500,500"), 100) }
                    .isInstanceOf(IllegalArgumentException::class.java)
                    .hasMessageContaining("unknown command")
        }

        test("small") {
            val input = File("in/day6-1").readLines()
            assertThat(litLights(input, 1000)).isEqualTo(569999)
        }
    }

    describe("grid implementation") {

        test("all disabled by default") {
            val grid = LightGrid(4)
            assertThat(grid.litCount()).isEqualTo(0)
        }

        test("lit all") {
            val grid = LightGrid(4)
            grid.setRegion(GridRegion(0, 0, 3, 3)) { 1 }

            assertThat(grid.litCount()).isEqualTo(16)
        }

        test("toggle all") {
            val grid = LightGrid(4)
            grid.setRegion(GridRegion(0, 0, 3, 3)) { 1 }
            assertThat(grid.litCount()).isEqualTo(16)

            grid.setRegion(GridRegion(0, 0, 3, 3)) { 0 }
            assertThat(grid.litCount()).isEqualTo(0)
        }
    }

    describe("follow santas instructions with dimmable lights"){

        test("turn on 0,0 through 0,0 would increase the total brightness by 1."){
            assertThat(litLightsWithBrightness(listOf("turn on 0,0 through 0,0"), 1000))
                    .isEqualTo(1)
        }

        test("toggle 0,0 through 999,999 would increase the total brightness by 2000000."){
            assertThat(litLightsWithBrightness(listOf("toggle 0,0 through 999,999"), 1000))
                    .isEqualTo(2000000)
        }

        test("big"){
            val input = File("in/day6-2").readLines()
            assertThat(litLightsWithBrightness(input, 1000)).isEqualTo(17836115)
        }
    }
})
