import com.pascalwelsch.aoc.resourceFileText
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

class Day5_NiceStringsTest {
    @Test
    fun `double - detect double chars`() {
        assertThat("aa".hasDouble()).isTrue()
        assertThat("bb".hasDouble()).isTrue()
    }

    @Test
    fun `double - no doubles`() {
        assertThat("ababababa".hasDouble()).isFalse()
        assertThat("asdfugknbfdgicrmopn".hasDouble()).isFalse()
    }

    @Test
    fun `double - empty`() {
        assertThat("".hasDouble()).isFalse()
    }

    @Test
    fun `detect 3 vowels - has 3`() {
        assertThat("ioe".hasVowels(3)).isTrue()
        assertThat("aeu".hasVowels(3)).isTrue()
    }

    @Test
    fun `detect 3 vowels - more`() {
        assertThat("ioeui".hasVowels(3)).isTrue()
    }

    @Test
    fun `detect 3 vowels - same vowels`() {
        assertThat("aaa".hasVowels(3)).isTrue()
        assertThat("eee".hasVowels(3)).isTrue()
        assertThat("iii".hasVowels(3)).isTrue()
        assertThat("ooo".hasVowels(3)).isTrue()
        assertThat("uuu".hasVowels(3)).isTrue()
    }

    @Test
    fun `detect 3 vowels - less`() {
        assertThat("qwert".hasVowels(3)).isFalse()
        assertThat("nnn".hasVowels(3)).isFalse()
    }

    @Test
    fun `detect 3 vowels - empty`() {
        assertThat("".hasVowels(3)).isFalse()
    }

    @Test
    fun `detect 3 vowels - wrong min count`() {
        assertThatThrownBy { "".hasVowels(0) }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessageContaining("1")

        assertThatThrownBy { "".hasVowels(-10) }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessageContaining("1")
    }

    // ugknbfddgicrmopn is nice because it has at least three vowels (u...i...o...), a double
    // letter (...dd...), and none of the disallowed substrings.
    @Test
    fun `find nice strings - at least three vowels`() {
        assertThat("ugknbfddgicrmopn".isNice()).isTrue()
    }

    // aaa is nice because it has at least three vowels and a double letter, even though the
    // letters used by different rules overlap.
    @Test
    fun `find nice strings - 3 vowels and double letter`() {
        assertThat("aaa".isNice()).isTrue()
    }

    // jchzalrnumimnmhp is naughty because it has no double letter.
    @Test
    fun `find nice strings - naughty`() {

    }

    //haegwjzuvuyypxyu is naughty because it contains the string xy.
    @Test
    fun `find nice strings - contains xy`() {
        assertThat("haegwjzuvuyypxyu".isNice()).isFalse()
    }

    // dvszwmarrgswjxmb is naughty because it contains only one vowel.
    @Test
    fun `find nice strings - only vowel`() {
        assertThat("dvszwmarrgswjxmb".isNice()).isFalse()
    }

    @Test
    fun `find nice strings - too short`() {
        assertThat("".isNice()).isFalse()
        assertThat("a".isNice()).isFalse()
        assertThat("aa".isNice()).isFalse()
    }

    @Test
    fun `find nice strings - small`() {
        val input = resourceFileText("day5-1").lines()
        val result = input.filter { it.isNice() }.count()
        assertThat(result).isEqualTo(255)
    }

    @Test
    fun `sandwich chars - is sandwich`() {
        assertThat("aba".containsSandwichChars()).isTrue()
        assertThat("asdfaqwqdsf".containsSandwichChars()).isTrue() // qwq
        assertThat("aba".containsSandwichChars()).isTrue()
        assertThat("aba".containsSandwichChars()).isTrue()
        assertThat("xyx".containsSandwichChars()).isTrue()
        assertThat("abcdefeghi".containsSandwichChars()).isTrue() // efe
        assertThat("aaa".containsSandwichChars()).isTrue() // efe
    }

    @Test
    fun `sandwich chars - not sandwich`() {
        assertThat("qwertyaqwerty".containsSandwichChars()).isFalse()
        assertThat("aabbaa".containsSandwichChars()).isFalse()
    }

    @Test
    fun `repeated doubles - find repeated doubles`() {
        assertThat("xyxy".containsDoublePairs()).isTrue()
        assertThat("aabcdefgaa".containsDoublePairs()).isTrue()
        assertThat("qwertabab".containsDoublePairs()).isTrue()
    }

    @Test
    fun `repeated doubles - no doubles`() {
        assertThat("".containsDoublePairs()).isFalse()
        assertThat("aff".containsDoublePairs()).isFalse()
        assertThat("affa".containsDoublePairs()).isFalse()
        assertThat("ieodomkazucvgmuy".containsDoublePairs()).isFalse()
    }

    @Test
    fun `repeated doubles - don't detect overlaps as false positives`() {
        assertThat("aaa".containsDoublePairs()).isFalse()
        assertThat("aa".containsDoublePairs()).isFalse()
    }

    // qjhvhtzxzqqjkmpb is nice because is has a pair that appears twice (qj) and a letter that
    // repeats with exactly one letter between them (zxz).
    @Test
    fun `new santa rules - sample 1`() {
        assertThat("qjhvhtzxzqqjkmpb".isNicer()).isTrue()
    }

    // xxyxx is nice because it has a pair that appears twice and a letter that repeats with one
    // between, even though the letters used by each rule overlap.
    @Test
    fun `new santa rules - sample 2`() {
        assertThat("xxyxx".isNicer()).isTrue()
    }

    // uurcxstgmygtbstg is naughty because it has a pair (tg) but no repeat with a single letter
    // between them.
    @Test
    fun `new santa rules - sample 3`() {
        assertThat("uurcxstgmygtbstg".isNicer()).isFalse()
    }

    // ieodomkazucvgmuy is naughty because it has a repeating letter with one between (odo), but
    // no pair that appears twice.
    @Test
    fun `new santa rules - sample 4`() {
        assertThat("ieodomkazucvgmuy".isNicer()).isFalse()
    }

    @Test
    fun `new santa rules - too short`() {
        assertThat("".isNicer()).isFalse()
        assertThat("a".isNicer()).isFalse()
        assertThat("aa".isNicer()).isFalse()
    }

    @Test
    fun `new santa rules - big`() {
        val input = resourceFileText("day5-2").lines()
        val result = input.filter { it.isNicer() }.count()
        assertThat(result).isEqualTo(55)
    }
}
