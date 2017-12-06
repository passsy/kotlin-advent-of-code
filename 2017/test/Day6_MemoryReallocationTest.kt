import Day6_MemoryReallocation.recursionLoopSize
import Day6_MemoryReallocation.redistribute
import Day6_MemoryReallocation.stepsUntilRecursion
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class Day6_MemoryReallocationTest {

    @Test
    fun `redistribute returns a new list instance`() {
        val input = listOf(1, 2, 3)
        val output = redistribute(input)
        assertThat(input).isNotSameAs(output)
    }

    @Test
    fun `redistribute - sample 0`() {
        val redistributed = redistribute(listOf(0, 2, 7, 0))
        assertThat(redistributed).isEqualTo(listOf(2, 4, 1, 2))
    }
    @Test
    fun `redistribute - sample 1`() {
        val redistributed = redistribute(listOf(2, 4, 1, 2))
        assertThat(redistributed).isEqualTo(listOf(3, 1, 2, 3))
    }

    @Test
    fun `redistribute - sample 2`() {
        val redistributed = redistribute(listOf(3 ,1 ,2, 3))
        assertThat(redistributed).isEqualTo(listOf(0, 2, 3, 4))
    }

    @Test
    fun `detect recursion - sample`() {
        val steps = stepsUntilRecursion(listOf(0, 2, 7, 0))
        assertThat(steps).isEqualTo(5)
    }

    @Test
    fun `recursion loop size - sample`() {
        val steps = recursionLoopSize(listOf(0, 2, 7, 0))
        assertThat(steps).isEqualTo(4)
    }
}