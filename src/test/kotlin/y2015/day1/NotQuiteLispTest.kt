package y2015.day1

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.io.File

@RunWith(JUnitPlatform::class)
class NotQuiteLispTest : Spek({

    describe("Santas final position") {

        test("(()) and ()() both result in floor 0.") {
            assertThat(whatFloor("(())")).isEqualTo(0)
            assertThat(whatFloor("()()")).isEqualTo(0)
        }

        test("((( and (()(()( both result in floor 3.") {
            assertThat(whatFloor("(((")).isEqualTo(3)
            assertThat(whatFloor("(()(()(")).isEqualTo(3)
        }

        test("))((((( also results in floor 3.") {
            assertThat(whatFloor("))(((((")).isEqualTo(3)
        }

        test("()) and ))( both result in floor -1 (the first basement level).") {
            assertThat(whatFloor("())")).isEqualTo(-1)
            assertThat(whatFloor("))(")).isEqualTo(-1)
        }

        test("))) and )())()) both result in floor -3.") {
            assertThat(whatFloor(")))")).isEqualTo(-3)
            assertThat(whatFloor(")())())")).isEqualTo(-3)
        }
    }

    test("real example input 1") {
        val file = File("in/day1-1")
        val input = file.readLines()[0]
        assertThat(whatFloor(input)).isEqualTo(138)
    }

    describe("When reaches Santa floor -1") {
        test(") causes him to enter the basement at character position 1.") {
            assertThat(whenEnterBasement(")")).isEqualTo(1)
        }

        test("()()) causes him to enter the basement at character position 5.") {
            assertThat(whenEnterBasement("()())")).isEqualTo(5)
        }

        test("crash when never reaching the basement") {
            assertThatThrownBy { whenEnterBasement("(") }
                    .isInstanceOf(IllegalStateException::class.java)
        }
    }

    test("real example") {
        val file = File("in/day1-2")
        val input = file.readLines()[0]
        assertThat(whenEnterBasement(input)).isEqualTo(1771)
    }
})