package lexer.recognizer

import lexer.TokenType.*
import java.util.regex.Pattern

interface Recognizer {
    fun number(): (String) -> Pair<Boolean, String>
    fun identifier(): (String) -> Pair<Boolean, String>
    fun keyword(): (String) -> Pair<Boolean, String>
    fun type(): (String) -> Pair<Boolean, String>
    fun boolean(): (String) -> Pair<Boolean, String>
    fun string():  (String) -> Pair<Boolean, String>
}

object RegexRecognizer: Recognizer {

    const val NUMBER_REGEX = "^-?\\d+(\\.\\d+)?"
    const val IDENTIFIER_REGEX = "^[a-zA-Z]([a-zA-Z]|\\d|_)*"
    // const val STRING_REGEX = "^\"((\${[a-zA-Z0-9]+})|[a-zA-Z\\d])*\"" not working
    const val STRING_REGEX = "^\"((\$[a-zA-Z0-9 ]+)|[a-zA-Z\\d ])*\""

    override fun number(): (String) -> Pair<Boolean, String> = {
        recognize(NUMBER_REGEX, it)
    }

    override fun identifier(): (String) -> Pair<Boolean, String> = {
        recognize(IDENTIFIER_REGEX, it)
    }

    override fun keyword(): (String) -> Pair<Boolean, String> {
        val regex = "^($If|$Else|$Import|$Function|$Structure)"
        return { recognize(regex, it) }
    }

    override fun type(): (String) -> Pair<Boolean, String> {
        val regex = "^($ListType|$NumberType|$StringType|$BooleanType|$UnitType)"
        return { recognize(regex, it) }
    }

    override fun boolean(): (String) -> Pair<Boolean, String> {
        val regex = "^($True|$False)"
        return { recognize(regex, it) }
    }

    override fun string():  (String) -> Pair<Boolean, String> = {
        recognize(STRING_REGEX, it)
    }

    private fun recognize(regex: String, input: String): Pair<Boolean, String> {
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(input)

        val isMatch = matcher.find()
        val matchedGroup = if (isMatch) matcher.group() else input

        return isMatch to matchedGroup
    }

}