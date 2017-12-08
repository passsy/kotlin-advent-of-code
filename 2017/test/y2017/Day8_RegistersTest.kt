package y2017

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import y2017.Day8_Registers.Condition
import y2017.Day8_Registers.Instruction
import y2017.Day8_Registers.Operation
import y2017.Day8_Registers.Operator.DEC
import y2017.Day8_Registers.Operator.INC
import y2017.Day8_Registers.Register
import y2017.Day8_Registers.Verification.*
import y2017.Day8_Registers.parseInstruction
import y2017.Day8_Registers.part1
import y2017.Day8_Registers.part2

class Day8_RegistersTest {

    @Test
    fun `parse instruction`() {
        assertThat(parseInstruction("b inc 5 if a > 1"))
                .isEqualTo(Instruction(Condition("a", GREATER, 1), Operation("b", INC, 5)))

        assertThat(parseInstruction("a inc 1 if b < 5"))
                .isEqualTo(Instruction(Condition("b", SMALLER, 5), Operation("a", INC, 1)))

        assertThat(parseInstruction("c dec -10 if a >= 1"))
                .isEqualTo(Instruction(Condition("a", GREATER_EQUALS, 1), Operation("c", DEC, -10)))

        assertThat(parseInstruction("c inc -20 if c == 10"))
                .isEqualTo(Instruction(Condition("c", EQUALS, 10), Operation("c", INC, -20)))
    }

    @Test
    fun `highest value without executions`() {
        val r = Register()
        assertThat(r.largestRegister()).isEqualTo(0)
    }

    @Test
    fun `highest register sample`() {
        val r = Register()
        r.execute(Instruction(Condition("a", GREATER, 1), Operation("b", INC, 5)))
        r.execute(Instruction(Condition("b", SMALLER, 5), Operation("a", INC, 1)))
        r.execute(Instruction(Condition("a", GREATER_EQUALS, 1), Operation("c", DEC, -10)))
        r.execute(Instruction(Condition("c", EQUALS, 10), Operation("c", INC, -20)))
        assertThat(r.largestRegister()).isEqualTo(1)
    }

    @Test
    fun `solve Part 1`() {
        assertThat(part1.test()).isEqualTo("2971")
    }

    @Test
    fun `solve Part 2`() {
        assertThat(part2.test()).isEqualTo("4254")
    }
}