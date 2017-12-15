package y2017

import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test
import y2017.Day14_DiskDefragmentation.buildGrid
import y2017.Day14_DiskDefragmentation.part1
import y2017.Day14_DiskDefragmentation.part2
import y2017.Day14_DiskDefragmentation.regionCount
import y2017.Day14_DiskDefragmentation.usedSquares


@Ignore("super slow tests ~1s each")
class Day14_DiskDefragmentationTest {

    @Test
    fun `used squares of sample 1`() {
        assertThat(buildGrid("flqrgnkx").usedSquares()).isEqualTo(8108)
    }

    @Test
    fun `solve part 1`() {
        assertThat(part1.test()).isEqualTo("8222")
    }

    @Test
    fun `region count of sample 1`() {
        assertThat(buildGrid("flqrgnkx").regionCount()).isEqualTo(1242)
    }


    @Test
    fun `solve part 2`() {
        assertThat(part2.test()).isEqualTo("1086")
    }


}