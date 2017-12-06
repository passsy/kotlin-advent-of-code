package y2015.day5

import containsDoublePairs
import containsSandwichChars
import hasDouble
import hasVowels
import isNice
import isNicer
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.io.File

@RunWith(JUnitPlatform::class)
class Day5_NiceStringsTest : Spek({

    describe("doubles") {
        test("detect double chars") {
            assertThat("aa".hasDouble()).isTrue()
            assertThat("bb".hasDouble()).isTrue()
        }

        test("no doubles") {
            assertThat("ababababa".hasDouble()).isFalse()
            assertThat("asdfugknbfdgicrmopn".hasDouble()).isFalse()
        }

        test("empty") {
            assertThat("".hasDouble()).isFalse()
        }
    }

    describe("detect 3 vowels") {
        test("has 3") {
            assertThat("ioe".hasVowels(3)).isTrue()
            assertThat("aeu".hasVowels(3)).isTrue()
        }
        test("has more than 3") {
            assertThat("ioeui".hasVowels(3)).isTrue()
        }
        test("same vowel") {
            assertThat("aaa".hasVowels(3)).isTrue()
            assertThat("eee".hasVowels(3)).isTrue()
            assertThat("iii".hasVowels(3)).isTrue()
            assertThat("ooo".hasVowels(3)).isTrue()
            assertThat("uuu".hasVowels(3)).isTrue()
        }

        test("less vowels") {
            assertThat("qwert".hasVowels(3)).isFalse()
            assertThat("nnn".hasVowels(3)).isFalse()
        }

        test("empty") {
            assertThat("".hasVowels(3)).isFalse()
        }

        test("wrong min count") {
            assertThatThrownBy { "".hasVowels(0) }
                    .isInstanceOf(IllegalArgumentException::class.java)
                    .hasMessageContaining("1")

            assertThatThrownBy { "".hasVowels(-10) }
                    .isInstanceOf(IllegalArgumentException::class.java)
                    .hasMessageContaining("1")
        }
    }

    describe("find nice strings") {

        test("ugknbfddgicrmopn is nice because it has at least three vowels (u...i...o...), " +
                "a double letter (...dd...), and none of the disallowed substrings.") {
            assertThat("ugknbfddgicrmopn".isNice()).isTrue()
        }

        test("aaa is nice because it has at least three vowels and a double letter, " +
                "even though the letters used by different rules overlap.") {
            assertThat("aaa".isNice()).isTrue()
        }

        test("jchzalrnumimnmhp is naughty because it has no double letter.") {
            assertThat("jchzalrnumimnmhp".isNice()).isFalse()
        }

        test("haegwjzuvuyypxyu is naughty because it contains the string xy.") {
            assertThat("haegwjzuvuyypxyu".isNice()).isFalse()
        }

        test("dvszwmarrgswjxmb is naughty because it contains only one vowel.") {
            assertThat("dvszwmarrgswjxmb".isNice()).isFalse()
        }

        test("too short string") {
            assertThat("".isNice()).isFalse()
            assertThat("a".isNice()).isFalse()
            assertThat("aa".isNice()).isFalse()
        }

        test("small") {
            val input = File("in/day5-1").readLines()
            val result = input.filter { it.isNice() }.count()
            assertThat(result).isEqualTo(255)
        }
    }

    describe("sandwich chars") {
        test("is sandwich") {
            assertThat("aba".containsSandwichChars()).isTrue()
            assertThat("asdfaqwqdsf".containsSandwichChars()).isTrue() // qwq
            assertThat("aba".containsSandwichChars()).isTrue()
            assertThat("aba".containsSandwichChars()).isTrue()
            assertThat("xyx".containsSandwichChars()).isTrue()
            assertThat("abcdefeghi".containsSandwichChars()).isTrue() // efe
            assertThat("aaa".containsSandwichChars()).isTrue() // efe
        }

        test("is no sandwich") {
            assertThat("qwertyaqwerty".containsSandwichChars()).isFalse()
            assertThat("aabbaa".containsSandwichChars()).isFalse()
        }
    }

    describe("repeated doubles") {
        test("find repeated doubles") {
            assertThat("xyxy".containsDoublePairs()).isTrue()
            assertThat("aabcdefgaa".containsDoublePairs()).isTrue()
            assertThat("qwertabab".containsDoublePairs()).isTrue()
        }

        test("no doubles") {
            assertThat("".containsDoublePairs()).isFalse()
            assertThat("aff".containsDoublePairs()).isFalse()
            assertThat("affa".containsDoublePairs()).isFalse()
            assertThat("ieodomkazucvgmuy".containsDoublePairs()).isFalse()
        }

        test("don't detect overlaps as false positives") {
            assertThat("aaa".containsDoublePairs()).isFalse()
            assertThat("aa".containsDoublePairs()).isFalse()
        }
    }

    describe("find nice strings according to santas new rules") {

        test("qjhvhtzxzqqjkmpb is nice because is has a pair that appears twice (qj) and a letter" +
                " that repeats with exactly one letter between them (zxz).") {
            assertThat("qjhvhtzxzqqjkmpb".isNicer()).isTrue()
        }

        test("xxyxx is nice because it has a pair that appears twice and a letter that repeats" +
                " with one between, even though the letters used by each rule overlap.") {
            assertThat("xxyxx".isNicer()).isTrue()
        }
        test("uurcxstgmygtbstg is naughty because it has a pair (tg) but no repeat with" +
                " a single letter between them.") {
            assertThat("uurcxstgmygtbstg".isNicer()).isFalse()
        }
        test("ieodomkazucvgmuy is naughty because it has a repeating letter with one between" +
                " (odo), but no pair that appears twice.") {
            assertThat("ieodomkazucvgmuy".isNicer()).isFalse()
        }

        test("too short string") {
            assertThat("".isNicer()).isFalse()
            assertThat("a".isNicer()).isFalse()
            assertThat("aa".isNicer()).isFalse()
        }

        test("big") {
            val input = File("in/day5-2").readLines()
            val result = input.filter { it.isNicer() }.count()
            assertThat(result).isEqualTo(55)
        }
    }
})
