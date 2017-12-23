package y2017

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import y2017.Day18_Duet.SoloPlayer
import y2017.Day18_Duet.duet
import y2017.Day18_Duet.parseInstruction
import y2017.Day18_Duet.part1
import y2017.Day18_Duet.part2


class Day18_DuetTest {

    @Test
    fun `sample 1`() {

        val instructions = listOf(
                parseInstruction("set a 1"),
                parseInstruction("add a 2"),
                parseInstruction("mul a a"),
                parseInstruction("mod a 5"),
                parseInstruction("snd a"),
                parseInstruction("set a 0"),
                parseInstruction("rcv a"),
                parseInstruction("jgz a -1"),
                parseInstruction("set a 1"),
                parseInstruction("jgz a -2")
        )
        val sound = SoloPlayer(instructions).apply { run() }.sounds.peek()

        assertThat(sound).isEqualTo(4)
    }

    @Test
    fun `solve part 1`() {
        assertThat(part1.test()).isEqualTo("3423")
    }

    @Test
    fun `sample 2`() {

        val instructions = listOf(
                parseInstruction("snd 1"),
                parseInstruction("snd 2"),
                parseInstruction("snd p"),
                parseInstruction("rcv a"),
                parseInstruction("rcv b"),
                parseInstruction("rcv c"),
                parseInstruction("rcv d")
        )

        assertThat(duet(instructions)).isEqualTo(3)
    }

    @Test
    fun `solve part 2`() {
        assertThat(part2.test()).isEqualTo("7493")
    }
}