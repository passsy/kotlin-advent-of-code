import Day1_Taxicab.Instruction
import Day1_Taxicab.Position
import Day1_Taxicab.Rotation.LEFT
import Day1_Taxicab.Rotation.RIGHT
import Day1_Taxicab.findFirstCrossing
import Day1_Taxicab.followInstructions
import Day1_Taxicab.parseInstructions
import Day1_Taxicab.part1
import Day1_Taxicab.part2
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class Day1_TaxicabTest {

    @Test
    fun `parse instructions`() {
        val instructions = parseInstructions("R2, L3")
        assertThat(instructions)
                .isEqualTo(listOf(Instruction(RIGHT, 2), Instruction(LEFT, 3)))
    }

    @Test
    fun `follow instructions sample 1`() {
        val pos = followInstructions(listOf(Instruction(RIGHT, 2), Instruction(LEFT, 3)))
        assertThat(pos).isEqualTo(Position(2, 3))
        assertThat(pos.distance()).isEqualTo(5)
    }

    @Test
    fun `follow instructions sample 2`() {
        val pos = followInstructions(
                listOf(Instruction(RIGHT, 2), Instruction(RIGHT, 2), Instruction(RIGHT, 2)))
        assertThat(pos).isEqualTo(Position(0, -2))
        assertThat(pos.distance()).isEqualTo(2)
    }

    @Test
    fun `follow instructions sample 3`() {
        val pos = followInstructions(listOf(
                Instruction(RIGHT, 5),
                Instruction(LEFT, 5),
                Instruction(RIGHT, 5),
                Instruction(RIGHT, 3)
        ))
        assertThat(pos.distance()).isEqualTo(12)
    }

    @Test
    fun `HQ location based on first double visit`() {
        val pos = findFirstCrossing(listOf(
                Instruction(RIGHT, 8),
                Instruction(RIGHT, 4),
                Instruction(RIGHT, 4),
                Instruction(RIGHT, 8)
        ))
        assertThat(pos.distance()).isEqualTo(4)
    }

    @Test
    fun `solve Part1`() {
        assertThat(part1.test()).isEqualTo("332")
    }

    @Test
    fun `solve Part2`() {
        assertThat(part2.test()).isEqualTo("166")
    }
}