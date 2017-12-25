package y2017

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import y2017.Day25_HaltingProblem.State
import y2017.Day25_HaltingProblem.TuringMachine
import y2017.Day25_HaltingProblem.Value.One
import y2017.Day25_HaltingProblem.Value.Zero


class Day25_HaltingProblemTest {
    @Test
    fun `sample 1`() {
        val states = listOf(
                State("A", isZero = {
                    write(One)
                    moveRight()
                    state = "B"
                }, isOne = {
                    write(Zero)
                    moveLeft()
                    state = "B"
                }),
                State("B", isZero = {
                    write(One)
                    moveLeft()
                    state = "A"
                }, isOne = {
                    write(One)
                    moveRight()
                    state = "A"
                })
        )

        val checksum = TuringMachine(states, "A").checksum(6)
        assertThat(checksum).isEqualTo(3)
    }
}