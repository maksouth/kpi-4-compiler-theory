package lexer.recognizer

import lexer.Lexer
import org.junit.Assert.*
import org.junit.Test

class RegexRecognizerTest {

    val regexRecognizer = RegexRecognizer

    @Test
    fun `number 123`() =
        println(regexRecognizer.number()("123"))

    @Test
    fun `number 123 dot 35`() =
        println(regexRecognizer.number()("123.35"))

    @Test
    fun `keyword fun doSmth()`() =
        println(regexRecognizer.keyword()("fun doSmth()"))

    @Test
    fun `keyword if eq(1, 2)`() =
        println(regexRecognizer.keyword()("if eq(1, 2)"))

    @Test
    fun `identifier snake_id_name`() =
        println(regexRecognizer.identifier()("snake_id_name = 45.12"))

    @Test
    fun `identifier simpleCamelName`() =
        println(regexRecognizer.identifier()("simpleCamelName= 45.12"))

    @Test fun `identifier simplename`() =
        println(regexRecognizer.identifier()("simplename= 45.12"))

    @Test fun `string "simple string"`() =
        println(regexRecognizer.string()("\"simple string\""))

    @Test fun `string ""`() =
        println(regexRecognizer.string()("\"\""))

}