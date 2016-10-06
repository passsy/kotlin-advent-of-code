package day4

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.io.File

@RunWith(JUnitPlatform::class)
class IdealStockingStufferTest : Spek({

    describe("find adventcoins") {
        test("If your secret key is abcdef, the answer is 609043, because" +
                " the MD5 hash of abcdef609043 starts with five zeroes (000001dbbfa...)," +
                " and it is the lowest such number to do so.") {
            assertThat(findAdventCoin("abcdef") { it.startsWith("00000") }).isEqualTo(609043)
        }

        test("If your secret key is pqrstuv, the lowest number it combines with to make" +
                " an MD5 hash starting with five zeroes is 1048970; " +
                "that is, the MD5 hash of pqrstuv1048970 looks like 000006136ef....") {
            assertThat(findAdventCoin("pqrstuv") { it.startsWith("00000") }).isEqualTo(1048970)
        }

        test("small") {
            val input = File("in/day4-1").readLines()
            val result = findAdventCoin(input[0]) { it.startsWith("00000") }
            assertThat(result).isEqualTo(346386)
        }
    }

    test("big") {
        val input = File("in/day4-2").readLines()
        val result = findAdventCoin(input[0]) { it.startsWith("000000") }
        assertThat(result).isEqualTo(9958218)
    }
})

