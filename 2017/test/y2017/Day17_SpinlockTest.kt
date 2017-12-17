package y2017

import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test
import y2017.Day17_Spinlock.part1
import y2017.Day17_Spinlock.part2
import y2017.Day17_Spinlock.shortCircuitSpinLock


class Day17_SpinlockTest {

    @Test
    fun `short-circuit spinlock sample`() {
        assertThat(shortCircuitSpinLock(3, 2017, 2017)).isEqualTo(638)
    }

    @Ignore("takes long 130ms")
    @Test
    fun `solve part 1`() {
        assertThat(part1.test()).isEqualTo("1971")
    }

    @Test
    fun `sample part 2`() {
        assertThat(shortCircuitSpinLock(303, 10, 2017)).isEqualTo(8)
    }

    @Ignore("takes ~30s")
    @Test
    fun `solve part 2`() {
        assertThat(part2.test()).isEqualTo("17202899")
    }
}