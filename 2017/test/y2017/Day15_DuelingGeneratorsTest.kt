package y2017

import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test
import y2017.Day15_DuelingGenerators.generator
import y2017.Day15_DuelingGenerators.judgeCount
import y2017.Day15_DuelingGenerators.part1
import y2017.Day15_DuelingGenerators.part2

class Day15_DuelingGeneratorsTest {

    private val testGeneratorA = generator(65, 16807)
    private val testGeneratorB = generator(8921, 48271)

    @Test
    fun `sample A generator values`() {
        assertThat(testGeneratorA.take(5).toList())
                .isEqualTo(listOf(1092455L, 1181022009L, 245556042L, 1744312007L, 1352636452L))
    }

    @Test
    fun `sample B generator values`() {
        assertThat(testGeneratorB.take(5).toList())
                .isEqualTo(listOf(430625591L, 1233683848L, 1431495498L, 137874439L, 285222916L))
    }

    @Test
    fun `judge sample`() {
        assertThat(judgeCount(testGeneratorA, testGeneratorB, 5)).isEqualTo(1)
    }

    @Ignore("executes too long")
    @Test
    fun `solve part one`() {
        assertThat(part1.test()).isEqualTo("609")
    }

    @Test
    fun `solve part one simplified`() {
        assertThat(judgeCount(testGeneratorA, testGeneratorB, 50_000)).isEqualTo(3)
    }

    @Ignore("executes too long")
    @Test
    fun `solve part two`() {
        assertThat(part2.test()).isEqualTo("253")
    }

    @Test
    fun `solve part two simplified`() {

        val generatorA2 = testGeneratorA.filter { it % 4L == 0L }
        val generatorB2 = testGeneratorB.filter { it % 8L == 0L }

        assertThat(judgeCount(generatorA2, generatorB2, 9_000)).isEqualTo(2)
    }

}