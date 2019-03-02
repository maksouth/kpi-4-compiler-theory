package math_lexer

import org.junit.Test

class LexerTest {

    lateinit var lexer: Lexer

    @Test fun `test 1 + 2`() {
        lexer = Lexer("1 + 2")
        println(lexer.allTokens())
    }

    @Test fun `test (5-4) + 7`() {
        lexer = Lexer("(5-4) + 7")
        println(lexer.allTokens())
    }

    @Test fun `test pi = 22 div 7`() {
        lexer = Lexer("pi = 22 / 7")
        println(lexer.allTokens())
    }

    @Test fun `test radius = 5`() {
        lexer = Lexer("radius = 5")
        println(lexer.allTokens())
    }

    @Test fun `test 12 dot 34 * 5e-9`() {
        lexer = Lexer("12.34 * 5e-9")
        println(lexer.allTokens())
    }

    @Test fun `test circle_circumference = 2 * pi * radius`() {
        lexer = Lexer("circle_circumference = 2 * pi * radius")
        println(lexer.allTokens())
    }

    @Test fun `test circle_circumference = e2 ~ pi * radius`() {
        lexer = Lexer("circle_circumference = e2 ~ pi * radius")
        println(lexer.allTokens())
    }

    @Test fun `test 12 dot 34 * 5e-9 new line circle_circumference = 2 * pi * radius`() {
        val source = """
            12.34 * 5e-9
            circle_circumference = 2 * pi * radius
        """.trimIndent()

        lexer = Lexer(source)
        println(lexer.allTokens())
    }
}