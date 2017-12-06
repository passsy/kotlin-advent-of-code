package y2015.day7

import asCircuitInstruction
import assembleCircuit
import max16bit
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.io.File
import kotlin.test.assertEquals
import CircuitInstruction
import Circuit
import Operation
import Signal
import StaticSignal
import Not


@RunWith(JUnitPlatform::class)
class Day7_BuildCircuitTest : Spek({

    describe("example") {

        test("""
        123 -> x
        456 -> y
        x AND y -> d
        x OR y -> e
        x LSHIFT 2 -> f
        y RSHIFT 2 -> g
        NOT x -> h
        NOT y -> i
        """) {
            val circuit = assembleCircuit(listOf("123 -> x", "456 -> y",
                    "x AND y -> d",
                    "x OR y -> e",
                    "x LSHIFT 2 -> f",
                    "y RSHIFT 2 -> g",
                    "NOT x -> h",
                    "NOT y -> i"))

            assertThat(circuit.get("d")).isEqualTo(72)
            assertThat(circuit.get("e")).isEqualTo(507)
            assertThat(circuit.get("f")).isEqualTo(492)
            assertThat(circuit.get("g")).isEqualTo(114)
            assertThat(circuit.get("h")).isEqualTo(65412)
            assertThat(circuit.get("i")).isEqualTo(65079)
            assertThat(circuit.get("x")).isEqualTo(123)
            assertThat(circuit.get("y")).isEqualTo(456)
        }
    }


    test("small") {
        val input = File("in/day7-1").readLines()
        val circuit = assembleCircuit(input)
        val result = circuit["a"]
        assertThat(result).isEqualTo(3176)

    }


    test("big") {
        val input = File("in/day7-2").readLines()
        val circuit = assembleCircuit(input)
        val oldResult = circuit["a"]

        circuit.inputCache.clear()

        // change output of b to output of previous a
        circuit.instructions.run {
            val signalB = first { it.output == "b" }
            remove(signalB)
            add(CircuitInstruction("b", StaticSignal(oldResult)))
        }

        val result = circuit["a"]
        assertThat(result).isEqualTo(14710)
    }

    describe("parse instructions") {
        val circuit = Circuit()

        test("unknown command") {
            assertThatThrownBy { "cq BLUBB cs -> ct".asCircuitInstruction(circuit) }
                    .isInstanceOf(IllegalArgumentException::class.java)
                    .hasMessageContaining("BLUBB")
        }

        test("operation") {
            val instruction = "cq AND cs -> ct".asCircuitInstruction(circuit)
            assertThat(instruction.output).isEqualTo("ct")
            val operation = instruction.input as Operation
            assertThat(operation.s1).isEqualTo(Signal("cq", circuit))
            assertThat(operation.s2).isEqualTo(Signal("cs", circuit))
        }

        test("signal") {
            val instruction = "cs -> ab".asCircuitInstruction(circuit)
            assertThat(instruction.output).isEqualTo("ab")
            val signal = instruction.input as Signal
            assertThat(signal.name).isEqualTo("cs")
        }

        test("not") {
            val instruction = "NOT 123 -> ab".asCircuitInstruction(circuit)
            assertThat(instruction.output).isEqualTo("ab")
            val signal = instruction.input as Not
            assertThat(signal.input).isEqualTo(StaticSignal(123))
        }
    }

    describe("Circuit") {
        test("get") {
            val circuit = Circuit()
            circuit.instructions.add(CircuitInstruction("a", StaticSignal(123)))
            assertThat(circuit.get("a")).isEqualTo(123)
        }

        test("get fails") {
            assertThatThrownBy { Circuit().get("test123") }
                    .isInstanceOf(IllegalArgumentException::class.java)
                    .hasMessageContaining("test123")
        }
        test("and") {
            val circuit = Circuit()
            circuit.instructions.add(CircuitInstruction("a", StaticSignal(123)))
            circuit.instructions.add(CircuitInstruction("b", StaticSignal(67)))
            circuit.instructions.add(CircuitInstruction("c", Operation(
                    Signal("a", circuit), Signal("b", circuit), OperationType.AND)))

            assertThat(circuit["c"]).isEqualTo(123 and 67)
        }
        test("or") {
            val circuit = Circuit()
            circuit.instructions.add(CircuitInstruction("a", StaticSignal(123)))
            circuit.instructions.add(CircuitInstruction("b", StaticSignal(67)))
            circuit.instructions.add(CircuitInstruction("c", Operation(
                    Signal("a", circuit), Signal("b", circuit), OperationType.OR)))

            assertThat(circuit["c"]).isEqualTo(123 or 67)
        }
        test("shift left") {
            val circuit = Circuit()
            circuit.instructions.add(CircuitInstruction("a", StaticSignal(123)))
            circuit.instructions.add(CircuitInstruction("b", StaticSignal(2)))
            circuit.instructions.add(CircuitInstruction("c", Operation(
                    Signal("a", circuit), Signal("b", circuit), OperationType.LSHIFT)))

            assertThat(circuit["c"]).isEqualTo(123 shl 2)
        }
        test("shift right") {
            val circuit = Circuit()
            circuit.instructions.add(CircuitInstruction("a", StaticSignal(123)))
            circuit.instructions.add(CircuitInstruction("b", StaticSignal(2)))
            circuit.instructions.add(CircuitInstruction("c", Operation(
                    Signal("a", circuit), Signal("b", circuit), OperationType.RSHIFT)))

            assertThat(circuit["c"]).isEqualTo(123 shr 2)
        }
        test("not") {
            val circuit = Circuit()
            circuit.instructions.add(CircuitInstruction("a", StaticSignal(123)))
            circuit.instructions.add(CircuitInstruction("c", Not(Signal("a", circuit))))

            assertThat(circuit["c"]).isEqualTo(max16bit - 123)
        }
    }

    describe("toString/hashcode methods of model classes") {

        test("Operation toString()") {
            val op = Operation(StaticSignal(1), StaticSignal(2), OperationType.AND)

            assertThat(op.toString())
                    .contains("1")
                    .contains("2")
                    .contains("AND")
        }

        test("Operation equals()/hashCode()") {
            val s1 = StaticSignal(1)
            val s2 = StaticSignal(2)
            val op1 = Operation(s1, s2, OperationType.AND)
            val op2 = Operation(s1, s2, OperationType.AND)
            assertEquals(op1, op2)
            assertEquals(op1.hashCode(), op2.hashCode())
        }

        test("Signal toString()") {
            val signal = Signal("ab", Circuit())

            assertThat(signal.toString())
                    .contains("ab")
        }

        test("Signal equals()/hashCode()") {
            val circuit = Circuit()
            val s1 = Signal("ab", circuit)
            val s2 = Signal("ab", circuit)

            assertEquals(s1, s2)
            assertEquals(s1.hashCode(), s2.hashCode())
        }

        test("StaticSignal toString()") {
            val signal = StaticSignal(11)

            assertThat(signal.toString())
                    .contains("11")
        }

        test("StaticSignal equals()/hashCode()") {
            val s1 = StaticSignal(1)
            val s2 = StaticSignal(1)

            assertEquals(s1.hashCode(), s2.hashCode())
            assertEquals(s1, s2)
        }

        test("Not toString()") {
            val not = Not(StaticSignal(25))
            assertThat(not.toString())
                    .contains("Not")
                    .contains("25")
        }

        test("Not equals()/hashCode()") {
            val signal = StaticSignal(25)
            val not1 = Not(signal)
            val not2 = Not(signal)

            assertEquals(not1, not2)
            assertEquals(not1.hashCode(), not2.hashCode())
        }
    }
})