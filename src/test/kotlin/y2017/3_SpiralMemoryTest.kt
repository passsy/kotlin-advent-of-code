package y2017

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


class SpiralMemoryTest {

    /**
     * Data from square 1 is carried 0 steps, since it's at the access port.
     */
    @Test
    fun `0 steps`() {
        assertThat(distance(1)).isEqualTo(0)
    }

    /**
     * Data from square 12 is carried 3 steps, such as: down, left, left.
     */
    @Test
    fun `right and up`() {
        assertThat(distance(12)).isEqualTo(3)
    }

    /**
     * Data from square 23 is carried only 2 steps: up twice.
     */
    @Test
    fun `straight down`() {
        assertThat(distance(23)).isEqualTo(2)
    }

    @Test
    fun `first 28 distances`() {
        assertThat((1..28).map(::distance)).isEqualTo(listOf(0,
                1, 2, 1, 2, 1, 2, 1, 2,
                3, 2, 3, 4, 3, 2, 3, 4, 3, 2, 3, 4, 3, 2, 3, 4,
                5, 4, 3) //...
        )
    }

    /**
     * Data from square 1024 must be carried 31 steps.
     */
    @Test
    fun `long distance`() {
        assertThat(distance(1024)).isEqualTo(31)
    }

    @Test
    fun `largest number - circle 1 move right and up`() {
        assertThat(firstLargerNumber(1)).isEqualTo(2)
    }
    @Test
    fun `largest number - circle 1 move left`() {
        assertThat(firstLargerNumber(3)).isEqualTo(4)
        assertThat(firstLargerNumber(4)).isEqualTo(5)
    }
    @Test
    fun `largest number - circle 1 move down`() {
        assertThat(firstLargerNumber(6)).isEqualTo(10)
        assertThat(firstLargerNumber(9)).isEqualTo(10)
        assertThat(firstLargerNumber(10)).isEqualTo(11)
    }

    @Test
    fun `largest number - move right`() {
        assertThat(firstLargerNumber(12)).isEqualTo(23)
        assertThat(firstLargerNumber(24)).isEqualTo(25)
    }
}