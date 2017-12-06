import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test

@Ignore("like bitcoin mining, heavy calculations, too heavy for unit tests")
class Day4_IdealStockingStufferTest {

    // If your secret key is abcdef, the answer is 609043, because the MD5 hash of abcdef609043
    // starts with five zeroes (000001dbbfa...), and it is the lowest such number to do so.
    @Test
    fun `find adventcoins sample 1`() {
        assertThat(findAdventCoin("abcdef") { it.startsWith("00000") }).isEqualTo(609043)
    }

    // "If your secret key is pqrstuv, the lowest number it combines with to make an MD5 hash
    // starting with five zeroes is 1048970; that is, the MD5 hash of pqrstuv1048970 looks
    // like 000006136ef...."
    @Test
    fun `find adventcoins sample 2`() {
        assertThat(findAdventCoin("pqrstuv") { it.startsWith("00000") }).isEqualTo(1048970)
    }

    @Test
    fun `solve part 1`() {
        val input = file("day4-1").readLines()
        val result = findAdventCoin(input[0]) { it.startsWith("00000") }
        assertThat(result).isEqualTo(346386)
    }

    @Test
    fun `solve part 2`() {
        val input = file("day4-2").readLines()
        val result = findAdventCoin(input[0]) { it.startsWith("000000") }
        assertThat(result).isEqualTo(9958218)
    }
}

