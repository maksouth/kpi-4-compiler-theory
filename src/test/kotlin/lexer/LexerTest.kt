package lexer

import org.junit.Test

class LexerTest {

    @Test fun `test number parameter = 25 dot 25`() {
        val source = "number parameter = 25.25"
        val lexer = Lexer(source)
        println(lexer.allTokens())
    }

    @Test fun `test if eq(25, 24) print("good @number") else print("bad")`() {
        val source = "if eq(25, 24) print(\"good number\") else print(\"bad\")"
        val lexer = Lexer(source)
        println(lexer.allTokens())
    }

    @Test fun `test two lines number parameter = 25 dot 25 new fun(parameter, 35 dot 25)`() {
        val source = "number parameter = 25.25 \n fun(parameter, 35.25)"
        val lexer = Lexer(source)
        println(lexer.allTokens())
    }
}