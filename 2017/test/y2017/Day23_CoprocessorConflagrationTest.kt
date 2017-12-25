package y2017

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import y2017.Day23_CoprocessorConflagration.part1
import y2017.Day23_CoprocessorConflagration.part2


class Day23_CoprocessorConflagrationTest {

    @Test
    fun `solve part 1`() {
        assertThat(part1.test()).isEqualTo("6241")
    }

    @Test
    fun `solve part 2`() {
        assertThat(part2.test()).isEqualTo("6241")
    }
}