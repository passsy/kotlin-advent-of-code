package day7

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.io.File


@RunWith(JUnitPlatform::class)
class BuildCircuitTest : Spek({

    describe("small circuit example") {

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
            add(Instruction("b", StaticSignal(oldResult)))
        }

        val result = circuit["a"]
        assertThat(result).isEqualTo(14710)
    }
})