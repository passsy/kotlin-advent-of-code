package y2017

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


class PassPhrasesTest {

    @Test
    fun `passphrase is valid for distinct words`() {
        assertThat(validatePassphrase("aa bb cc dd ee")).isTrue()
    }

    @Test
    fun `passphrase is valid altough substrings match`() {
        assertThat(validatePassphrase("aa bb cc dd aaa")).isTrue()
    }

    @Test
    fun `passphrase is invalid for same words`() {
        assertThat(validatePassphrase("aa bb cc dd aa")).isFalse()
    }

    @Test
    fun `hard passphrase is valid without anagrams`() {
        assertThat(validatePassphrase2("iiii oiii ooii oooi oooo")).isTrue()
    }

    @Test
    fun `hard passphrase is invalid with anagrams`() {
        assertThat(validatePassphrase2("oiii ioii iioi iiio")).isFalse()
    }
}