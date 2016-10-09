package day7

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith


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
})