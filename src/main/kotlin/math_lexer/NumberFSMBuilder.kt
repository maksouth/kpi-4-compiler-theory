package math_lexer

import fsm.State
import java.util.regex.Pattern

fun buildNumberRecognizer(): (String) -> Pair<Boolean, String> = {
    val regex = "\\d+(\\.\\d)*"
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(it)

    val isNumber = matcher.matches()
    val end = matcher.regionEnd()
    val number = it.substring(0, end)

    isNumber to number
}

object InitialState: State() {
    override fun nextInternal(input: Char) =
            if (input.isDigit()) Integer
            else null
}

object Integer: State() {
    override fun nextInternal(input: Char) = when {
        input.isDigit() -> Integer
        input == '.' -> BeginNumberWithFractionalPart
        input.toLowerCase() == 'e' -> BeginNumberWithExponent
        else -> null
    }

}

object BeginNumberWithFractionalPart: State() {
    override fun nextInternal(input: Char) = when {
        input.isDigit() -> NumberWithFractionalPart
        else -> null
    }
}

object NumberWithFractionalPart: State() {
    override fun nextInternal(input: Char) = when {
        input.isDigit() -> NumberWithFractionalPart
        input.toLowerCase() == 'e' -> BeginNumberWithExponent
        else -> null
    }
}

object BeginNumberWithExponent: State() {
    override fun nextInternal(input: Char) = when {
        input == '+' -> BeginNumberWithSignedExponent
        input == '-' -> BeginNumberWithSignedExponent
        input.isDigit() -> NumberWithExponent
        else -> null
    }
}

object BeginNumberWithSignedExponent: State() {
    override fun nextInternal(input: Char) = when {
        input.isDigit() -> NumberWithExponent
        else -> null
    }
}

object NumberWithExponent: State() {
    override fun nextInternal(input: Char): State? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


