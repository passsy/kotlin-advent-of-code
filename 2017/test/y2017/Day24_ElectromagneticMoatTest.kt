package y2017

import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test
import y2017.Day24_ElectromagneticMoat.Component
import y2017.Day24_ElectromagneticMoat.buildBridges
import y2017.Day24_ElectromagneticMoat.part1
import y2017.Day24_ElectromagneticMoat.strength


class Day24_ElectromagneticMoatTest {

    @Test
    fun `sample 1`() {
        val components = setOf(
                Component(0, 2),
                Component(2, 2),
                Component(2, 3),
                Component(3, 4),
                Component(3, 5),
                Component(0, 1),
                Component(10, 1),
                Component(9, 10))

        val bridge = buildBridges(components).maxBy { it.strength() }!!
        assertThat(bridge.strength()).isEqualTo(31)
    }

    @Ignore("too large (115ms)")
    @Test
    fun `solve part 1`() {
        assertThat(part1()).isEqualTo("1906")
    }
}