package recognizer

import fsm.FSM
import fsm.State
import java.util.regex.Pattern

fun buildRegexNumberRecognizer(): (String) -> Pair<Boolean, String> = {
    val regex = "\\d+(\\.\\d+)?([eE][+-]?\\d+)?"
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(it)

    val isNumber = matcher.find()
    val number = matcher.group()

    isNumber to number
}

fun buildFSMNumberRecognizer(): (String) -> Pair<Boolean, String> = FSM (
    initialState = InitialState,
    acceptingStates = listOf(Integer, NumberWithFractionalPart, NumberWithExponent)
)

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


