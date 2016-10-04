package day1

import org.assertj.core.api.KotlinAssertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe

class NotQuiteLispTest : Spek({

    describe("(()) and ()() both result in floor 0.") {
        assertThat(whatFloor("(())")).isEqualTo(0)
        assertThat(whatFloor("()()")).isEqualTo(0)
    }

    describe("((( and (()(()( both result in floor 3.") {
        assertThat(whatFloor("(((")).isEqualTo(3)
        assertThat(whatFloor("(()(()(")).isEqualTo(3)
    }

    describe("))((((( also results in floor 3.") {
        assertThat(whatFloor("))(((((")).isEqualTo(3)
    }

    describe("()) and ))( both result in floor -1 (the first basement level).") {
        assertThat(whatFloor("())")).isEqualTo(-1)
        assertThat(whatFloor("))(")).isEqualTo(-1)
    }

    describe("))) and )())()) both result in floor -3.") {
        assertThat(whatFloor(")))")).isEqualTo(-3)
        assertThat(whatFloor(")())())")).isEqualTo(-3)
    }

    /*describe("real example input 1") {
        val file = File("in/day1-1")
        val input file.readLines()[0]
        assertThat(whatFloor(input)).isEqualTo(138)
    }*/
})