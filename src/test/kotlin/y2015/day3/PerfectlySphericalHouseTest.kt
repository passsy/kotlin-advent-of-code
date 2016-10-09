package y2015.day2

import y2015.day3.numberOfVisitedHouses
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.io.File

@RunWith(JUnitPlatform::class)
class PerfectlySphericalHouseTest : Spek({

    describe("number of houses") {

        test("> delivers presents to 2 houses: one at the starting location, and one to the east.") {
            assertThat(numberOfVisitedHouses(">")).isEqualTo(2)
        }

        test("^>v< delivers presents to 4 houses in a square, including twice to the house at his starting/ending location.") {
            assertThat(numberOfVisitedHouses("^>v<")).isEqualTo(4)
        }

        test("^v^v^v^v^v delivers a bunch of presents to some very lucky children at only 2 houses.") {
            assertThat(numberOfVisitedHouses("^v^v^v^v^v")).isEqualTo(2)
        }

        test("parse unexpected character") {
            assertThatThrownBy { numberOfVisitedHouses("<><>asdf<>") }
                    .isInstanceOf(IllegalStateException::class.java)
                    .hasMessageContaining("a")
        }

        test("big") {
            val input = File("in/day3-1").readLines()
            val result = numberOfVisitedHouses(input[0])
            assertThat(result).isEqualTo(2572)
        }
    }

    describe("number of houses with santa and robo santa") {

        test("^v delivers presents to 3 houses, because Santa goes north, and then Robo-Santa goes south.") {
            assertThat(numberOfVisitedHouses("^v", 2)).isEqualTo(3)
        }

        test("^>v< now delivers presents to 3 houses, and Santa and Robo-Santa end up back where they started.") {
            assertThat(numberOfVisitedHouses("^>v<", 2)).isEqualTo(3)
        }

        test("^v^v^v^v^v now delivers presents to 11 houses, with Santa going one direction and Robo-Santa going the other.") {
            assertThat(numberOfVisitedHouses("^v^v^v^v^v", 2)).isEqualTo(11)
        }

        test("at least one santa required") {
            assertThatThrownBy { numberOfVisitedHouses("<", 0) }
                    .isInstanceOf(IllegalArgumentException::class.java)
                    .hasMessageContaining("one santa required")
        }

        test("big") {
            val input = File("in/day3-2").readLines()
            val result = numberOfVisitedHouses(input[0], 2)
            assertThat(result).isEqualTo(2631)
        }
    }

})