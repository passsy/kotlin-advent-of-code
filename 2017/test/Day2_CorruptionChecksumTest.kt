import Day2_CorruptionChecksum.checksum
import Day2_CorruptionChecksum.evenDivisionPair
import Day2_CorruptionChecksum.evenlyDivisibleChecksum
import Day2_CorruptionChecksum.minMaxDiff
import Day2_CorruptionChecksum.parseInputTable
import Day2_CorruptionChecksum.part1
import Day2_CorruptionChecksum.part2
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.Test

class Day2_CorruptionChecksumTest {

    @Test
    fun parseTable() {
        val table = listOf(
                "5\t1\t9\t5",
                "7\t5\t3",
                "2\t4\t6\t8")
        assertThat(parseInputTable(table)).isEqualTo(listOf(
                listOf(5, 1, 9, 5),
                listOf(7, 5, 3),
                listOf(2, 4, 6, 8)
        ))
    }

    @Test
    fun `checksum per row`() {
        assertThat(minMaxDiff(listOf(5, 1, 9, 3))).isEqualTo(8)
        assertThat(minMaxDiff(listOf(7, 5, 3))).isEqualTo(4)
        assertThat(minMaxDiff(listOf(2, 4, 6, 8))).isEqualTo(6)
    }

    @Test
    fun `checksum for table`() {
        val table = listOf(
                listOf(5, 1, 9, 5),
                listOf(7, 5, 3),
                listOf(2, 4, 6, 8)
        )
        assertThat(checksum(table)).isEqualTo(18)
    }

    @Test
    fun `evenly dividable checksum per row`() {
        assertThat(evenDivisionPair(listOf(5, 9, 2, 8))).isEqualTo(8 to 2)
        assertThat(evenDivisionPair(listOf(9, 4, 7, 3))).isEqualTo(9 to 3)
        assertThat(evenDivisionPair(listOf(3, 8, 6, 5))).isEqualTo(6 to 3)
    }

    @Test
    fun `evenly divisionable checksum - no pair throws`() {
        assertThat(catchThrowable {
            evenDivisionPair(listOf(3, 5, 7, 11, 13))
        }).hasMessageContaining("no evenly division pair")
    }

    @Test
    fun `evenly dividable checksum for table`() {
        val table = listOf(
                listOf(5, 9, 2, 8),
                listOf(9, 4, 7, 3),
                listOf(3, 8, 6, 5)
        )
        assertThat(evenlyDivisibleChecksum(table)).isEqualTo(9)
    }

    @Test
    fun `solve Part1`() {
        assertThat(part1.test()).isEqualTo("42378")
    }

    @Test
    fun `solve Part2`() {
        assertThat(part2.test()).isEqualTo("246")
    }
}