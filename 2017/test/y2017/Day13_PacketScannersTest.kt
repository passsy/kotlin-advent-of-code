package y2017

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import y2017.Day14_PacketScanners.Layer
import y2017.Day14_PacketScanners.fastestPass
import y2017.Day14_PacketScanners.part1
import y2017.Day14_PacketScanners.part2
import y2017.Day14_PacketScanners.severity


class Day13_PacketScannersTest {

    @Test
    fun `sample 1`() {
        val layers = listOf(
                Layer(0, 3),
                Layer(1, 2),
                Layer(4, 4),
                Layer(6, 4)
        )
        assertThat(severity(layers)).isEqualTo(24)
    }

    @Test
    fun `solve part 1`() {
        assertThat(part1.test()).isEqualTo("1588")
    }

    @Test
    fun `sample 2`() {
        val layers = listOf(
                Layer(0, 3),
                Layer(1, 2),
                Layer(4, 4),
                Layer(6, 4)
        )
        assertThat(fastestPass(layers)).isEqualTo(10)
    }

    @Test
    fun `solve part 2`() {
        assertThat(part2.test()).isEqualTo("3865118")
    }
}