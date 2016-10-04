package day2

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import java.io.File

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

    test("real example") {
        TODO()
        val file = File("in/day2-2")
        val input = file.readLines()[0]
    }
})