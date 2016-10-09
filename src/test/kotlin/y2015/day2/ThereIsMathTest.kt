package y2015.day2

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.io.BufferedWriter
import java.io.File

@RunWith(JUnitPlatform::class)
class ThereIsMathTest : Spek({

    describe("calculate wrapping paper") {

        test("A present with dimensions 2x3x4 requires 2*6 + 2*12 + 2*8 = 52 square feet of wrapping paper plus 6 square feet of slack, for a total of 58 square feet.") {
            assertThat(requiredPaper("2x3x4")).isEqualTo(58)
        }

        test("A present with dimensions 1x1x10 requires 2*1 + 2*10 + 2*10 = 42 square feet of wrapping paper plus 1 square foot of slack, for a total of 43 square feet.") {
            assertThat(requiredPaper("1x1x10")).isEqualTo(43)
        }
    }

    test("combined paper required") {
        val input = File("in/day2-1").readLines()
        val result = input.map { requiredPaper(it) }.sum()
        assertThat(result).isEqualTo(1598415)
    }

    describe("calculate ribbon") {

        test("A present with dimensions 2x3x4 requires 2+2+3+3 = 10 feet of ribbon to wrap the present plus 2*3*4 = 24 feet of ribbon for the bow, for a total of 34 feet.") {
            assertThat(requiredRibbon("2x3x4")).isEqualTo(34)
        }

        test("A present with dimensions 1x1x10 requires 1+1+1+1 = 4 feet of ribbon to wrap the present plus 1*1*10 = 10 feet of ribbon for the bow, for a total of 14 feet.") {
            assertThat(requiredRibbon("1x1x10")).isEqualTo(14)
        }
    }

    test("combined ribbon required") {
        val input = File("in/day2-2").readLines()
        val result = input.map { requiredRibbon(it) }.sum()
        assertThat(result).isEqualTo(3812909)
    }
})