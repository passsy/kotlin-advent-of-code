import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

class Day3_PerfectlySphericalHouseTest {

    // > delivers presents to 2 houses: one at the starting location, and one to the east.
    @Test
    fun `number of houses - initial step`() {
        assertThat(numberOfVisitedHouses(">")).isEqualTo(2)
    }

    // ^>v< delivers presents to 4 houses in a square, including twice to the house at his starting/ending location.
    @Test
    fun `number of houses - smallest round`() {
        assertThat(numberOfVisitedHouses("^>v<")).isEqualTo(4)
    }

    // ^v^v^v^v^v delivers a bunch of presents to some very lucky children at only 2 houses.
    @Test
    fun `number of houses - alternate up down`() {
        assertThat(numberOfVisitedHouses("^v^v^v^v^v")).isEqualTo(2)
    }

    @Test
    fun `number of houses - parse unexpected character`() {
        assertThatThrownBy { numberOfVisitedHouses("<><>asdf<>") }
                .isInstanceOf(IllegalStateException::class.java)
                .hasMessageContaining("a")
    }

    @Test
    fun `number of houses - big`() {
        val input = file("day3-1").readLines()
        val result = numberOfVisitedHouses(input[0])
        assertThat(result).isEqualTo(2572)
    }

    //^v delivers presents to 3 houses, because Santa goes north, and then Robo-Santa goes south.
    @Test
    fun `robo santa - up down`() {
        assertThat(numberOfVisitedHouses("^v", 2)).isEqualTo(3)
    }

    // ^>v< now delivers presents to 3 houses, and Santa and Robo-Santa end up back where they started.
    @Test
    fun `robo santa - small round`() {
        assertThat(numberOfVisitedHouses("^>v<", 2)).isEqualTo(3)
    }

    // ^v^v^v^v^v now delivers presents to 11 houses, with Santa going one direction and Robo-Santa going the other.
    @Test
    fun `robo santa - alternate up down`() {

    }

    @Test
    fun `robo santa - at least one santa required`() {
        assertThatThrownBy { numberOfVisitedHouses("<", 0) }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessageContaining("one santa required")
    }

    @Test
    fun `robo santa - big`() {
        val input = file("day3-2").readLines()
        val result = numberOfVisitedHouses(input[0], 2)
        assertThat(result).isEqualTo(2631)
    }

}