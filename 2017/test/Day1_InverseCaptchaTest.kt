import Day1_InverseCaptcha.captcha1
import Day1_InverseCaptcha.captcha2
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.Test

class Day1_InverseCaptchaTest {

    /**
     * `1122` produces a sum of 3 (1 + 2) because the first digit (1) matches the second digit and the third digit (2) matches the fourth digit.
     */
    @Test
    fun `captcha1 - repeating digits`() {
        assertThat(captcha1("1122")).isEqualTo(3)
    }

    /**
     * 1111 produces 4 because each digit (all 1) matches the next.
     */
    @Test
    fun `captcha1 - all digits are the same`() {
        assertThat(captcha1("1111")).isEqualTo(4)
    }

    /**
     * 1234 produces 0 because no digit matches the next.
     */
    @Test
    fun `captcha1 - no repetition`() {
        assertThat(captcha1("1234")).isEqualTo(0)
    }

    /**
     * 91212129 produces 9 because the only digit that matches the next one is the last digit, 9.
     */
    @Test
    fun `captcha1 - circular repetition only`() {
        assertThat(captcha1("91212129")).isEqualTo(9)
    }

    /**
     * 1212 produces 6: the list contains 4 items, and all four digits match the digit 2 items ahead.
     */
    @Test
    fun `captcha2 - multiple repetitions`() {
        assertThat(captcha2("1212")).isEqualTo(6)
    }

    /**
     * 1221 produces 0, because every comparison is between a 1 and a 2.
     */
    @Test
    fun `captcha2 - no repetition`() {
        assertThat(captcha2("1221")).isEqualTo(0)
    }

    /**
     * 123425 produces 4, because both 2s match each other, but no other digit has a match.
     */
    @Test
    fun `captcha2 - only one match`() {
        assertThat(captcha2("123425")).isEqualTo(4)
    }

    /**
     * 123123 produces 12
     */
    @Test
    fun `captcha2 - 123123 produces 12`() {
        assertThat(captcha2("123123")).isEqualTo(12)
    }

    /**
     * 12131415 produces 4.
     */
    @Test
    fun `captcha2 - 12131415 produces 4`() {
        assertThat(captcha2("12131415")).isEqualTo(4)
    }

    @Test
    fun `captcha2 - requires even input`() {
        val throwable = catchThrowable {
            captcha2("123")
        }
        assertThat(throwable).isInstanceOf(IllegalArgumentException::class.java)
                .hasMessageContaining("even")
    }
}