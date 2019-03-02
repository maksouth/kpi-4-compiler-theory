package math_lexer

import org.junit.Assert.*
import org.junit.Test

class NumberFSMBuilderKtTest {

    val numberRecognizer = buildRegexNumberRecognizer()

    @Test fun `test 123`() {
        val input = "123"
        val (matches, number) = numberRecognizer(input)
        println("matches $matches number $number")

        assertEquals(number, input)
        assertTrue(matches)
    }

    @Test fun `test 123 dot 23`() {
        val input = "123.23"
        val (matches, number) = numberRecognizer(input)
        println("matches $matches number $number")

        assertEquals(number, input)
        assertTrue(matches)
    }

    @Test fun `test 123 dot 23 e 4`() {
        val input = "123.23e4"
        val (matches, number) = numberRecognizer(input)
        println("matches $matches number $number")

        assertEquals(number, input)
        assertTrue(matches)
    }

    @Test fun `test 123 dot 23 e -45`() {
        val input = "123.23e-45"
        val (matches, number) = numberRecognizer(input)
        println("matches $matches number $number")

        assertEquals(number, input)
        assertTrue(matches)
    }

    @Test fun `test 123 e +22`() {
        val input = "123e+22"
        val (matches, number) = numberRecognizer(input)
        println("matches $matches number $number")

        assertEquals(number, input)
        assertTrue(matches)
    }

    @Test fun `test 123 + 22`() {
        val input = "123 + 22"
        val (matches, number) = numberRecognizer(input)
        println("matches $matches number $number")

        assertEquals(number, "123")
        assertTrue(matches)
    }

    @Test fun `test 123 - e4`() {
        val input = "123 - e4"
        val (matches, number) = numberRecognizer(input)
        println("matches $matches number $number")

        assertEquals(number, "123")
        assertTrue(matches)
    }
}