package lexer

import recognizer.Recognizer
import recognizer.RegexRecognizer

class Lexer(private val input: String) {
    private var currentParsePosition = 0
    private var currentParseLine = 0
    private var currentParseColumn = 0

    private val recognizer: Recognizer = RegexRecognizer

    fun allTokens(): List<Token> {
        var token = nextToken()
        val tokens = mutableListOf<Token>()

        while (token.tokenType != TokenType.EndOfInput
            && token.tokenType != TokenType.Other) {
            tokens += token
            token = nextToken()
        }

        return tokens
    }

    fun nextToken(): Token {
        skipWhiteSpacesAndNewLines()
        return if (currentParsePosition >= input.length)
            Token(TokenType.EndOfInput, "", currentParseLine, currentParseColumn)
        else recognizeLexem(input[currentParsePosition])
    }

    private fun skipWhiteSpacesAndNewLines() {
        while (currentParsePosition < input.length &&
                input[currentParsePosition].isWhitespaceOrNewLine()) {

            if (input[currentParsePosition].isNewLine()) {
                currentParseLine++
                currentParseColumn = 0
            } else currentParseColumn++

            currentParsePosition++
        }
    }

    fun recognizeLexem(char: Char) = when {
        char.isLetter() -> recognizeIdentifier()
        char.isDigit() -> recognizeNumber()
        char.isComparison() -> recognizeComparisonOperator()
        char.isArithmetic() -> recognizeArithmeticOperator()
        char.isParenthesis() -> recognizeParenthesis()
        else -> unknownToken()
    }


    fun recognizeIdentifier(): Token {
        var identifier = ""
        var position = currentParsePosition
        val line = currentParseLine
        val column = currentParseColumn

        while (position < input.length) {
            val character = input[position]

            if (!(character.isLetterOrDigit() || character.isUnderscore()))
                break

            identifier += character
            position++
        }

        currentParsePosition += identifier.length
        currentParseColumn += identifier.length

        return Token(TokenType.Identifier, identifier, line, column)
    }

    fun recognizeComparisonOperator(): Token {
        val position = currentParsePosition
        val line = currentParseLine
        val column = currentParseColumn
        val character = input[position]

        val lookahead =
            if(position + 1 < input.length) input[position + 1]
            else null

        val isLookaheadEqualSymbol = lookahead ?: lookahead == '='

        currentParsePosition++
        currentParseColumn++

        if (isLookaheadEqualSymbol) {
            currentParsePosition++
            currentParseColumn++
        }

        return when(character) {
            '>' -> if (isLookaheadEqualSymbol) Token(TokenType.GreaterThanOrEqual, ">=", line, column)
                    else Token(TokenType.GreaterThan, ">", line, column)
            '<' -> if (isLookaheadEqualSymbol) Token(TokenType.LessThanOrEqual, "<=", line, column)
                    else Token(TokenType.LessThan, "<", line, column)
            '=' -> if (isLookaheadEqualSymbol) Token(TokenType.Equal, "==", line, column)
                    else Token(TokenType.Assign, "=", line, column)
            else -> throw IllegalStateException("no comparison operator at " +
                    "position $position column $column line $line")
        }
    }

    fun recognizeArithmeticOperator(): Token {
        val position = currentParsePosition
        val line = currentParseLine
        val column = currentParseColumn
        val character = input[position]

        currentParsePosition++
        currentParseColumn++

        return when(character) {
            '+' -> Token(TokenType.Plus, "+", line, column)
            '-' -> Token(TokenType.Minus, "-", line, column)
            '*' -> Token(TokenType.Times, "*", line, column)
            '/' -> Token(TokenType.Div, "/", line, column)
            else -> throw IllegalStateException("no comparison operator at " +
                    "position $position column $column line $line")
        }
    }

    fun recognizeParenthesis(): Token {
        val position = currentParsePosition
        val line = currentParseLine
        val column = currentParseColumn
        val character = input[position]

        currentParsePosition++
        currentParseColumn++

        return if (character == '(')
            Token(TokenType.LeftParenthesis, "(", line, column)
        else Token(TokenType.RightParenthesis, ")", line, column)
    }

    fun recognizeNumber(): Token {
        val line = currentParseLine
        val column = currentParseColumn

        val recognizer = recognizer.number()

        val fsmInput = input.substring(currentParsePosition)

        val (recognized, number) = recognizer(fsmInput)

        if (recognized) {
            currentParsePosition += number.length
            currentParseColumn += number.length

            return Token(TokenType.Number, number, line, column)
        }

        return unknownToken()
    }

    fun unknownToken(): Token = Token(
        TokenType.Other,
        "unknown",
        currentParseLine,
        currentParseColumn
    )
}

data class Token(
    val tokenType: TokenType,
    val value: String,
    val line: Int,
    val column: Int) {
    override fun toString() = value
}

fun Char.isWhitespaceOrNewLine() = isWhitespace() or isNewLine()

fun Char.isNewLine() = this == '\n'

fun Char.isUnderscore() = this == '_'

fun Char.isOperator() = isComparison() or isArithmetic()

fun Char.isComparison() = when(this) {
    '>', '<', '=' -> true
    else -> false
}

fun Char.isArithmetic() = when(this) {
    '+', '-', '*', '/' -> true
    else -> false
}

fun Char.isParenthesis() = when(this) {
    '(', ')' -> true
    else -> false
}
