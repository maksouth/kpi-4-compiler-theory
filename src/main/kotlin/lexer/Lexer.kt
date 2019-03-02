package lexer

import lexer.recognizer.Recognizer
import lexer.recognizer.RegexRecognizer
import kotlin.Boolean
import kotlin.Char
import kotlin.Pair
import kotlin.String

class Lexer(private val input: String) {

    private var currentParsePosition = 0
    private var currentParseLine = 0
    private var currentParseColumn = 0

    private val recognizerFactory: Recognizer = RegexRecognizer

    fun allTokens(): List<Token> {
        var token = nextToken()
        val tokens = mutableListOf<Token>()

        while (token.type != TokenType.EndOfInput
            && token.type != TokenType.Unrecognized) {
            tokens += token
            token = nextToken()
        }

        return tokens
    }

    fun nextToken(): Token {
        skipWhiteSpacesAndNewLines()
        return if (currentParsePosition >= input.length)
            Token(TokenType.EndOfInput, currentParseLine, currentParseColumn)
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

    fun recognizeLexem(char: Char): Token = when {
        char.isLetter() -> recognizeCharSequence()
        char.isQuote() -> recognizeString()
        char.isDigit() -> recognizeNumber()
        char.isMinus() -> recognizeNumber()
        char.isBrackets() -> recognizeBrackets()
        else -> recognizeSpecialSymbols()
    }

    fun recognizeCharSequence(): Token =
        recognizeKeyword() ?:
        recognizeType() ?:
        recognizeBoolean() ?:
        recognizeIdentifier() ?:
        unknownToken()

    fun recognizeKeyword(): Token? = recognizeLanguageWord(
        recognizerFactory.keyword(),
        ::getKeywordType
    )

    fun recognizeType(): Token? = recognizeLanguageWord(
        recognizerFactory.type(),
        ::getStandartType
    )

    fun recognizeBoolean(): Token? = recognizeLanguageWord(
        recognizerFactory.boolean(),
        ::getBooleanType
    )

    fun recognizeIdentifier(): Token? = recognizeLanguageWord(
        recognizerFactory.identifier(),
        {TokenType.Identifier}
    )

    fun recognizeNumber(): Token = recognizeLanguageWord(
        recognizerFactory.number(),
        {TokenType.Number},
        default = unknownToken()
    )!!

    fun recognizeString(): Token = recognizeLanguageWord(
        recognizerFactory.string(),
        {TokenType.StringType},
        default = unknownToken()
    )!!

    fun recognizeLanguageWord(
        recognizer: (String) -> Pair<Boolean, String>,
        typeResolver: (String) -> TokenType,
        default: Token? = null
    ): Token? {
        val (isKeyword, keyword) = recognizer(input.substring(currentParsePosition))

        if (!isKeyword) return default

        val type = typeResolver(keyword)
        return sequenceSymbolToken(type, keyword)
    }

    fun recognizeBrackets(): Token = with(input[currentParsePosition]) {
        val type = getBracketType(this)
        oneSymbolToken(type)
    }


    fun recognizeSpecialSymbols(): Token =
        if(input.startsWith(TokenType.Arrow.literal))
            sequenceSymbolToken(TokenType.Arrow)
        else with(input[currentParsePosition]) {
            val tokenType = getSpecialSymbolType(this)
            oneSymbolToken(tokenType)
        }

    fun oneSymbolToken(type: TokenType): Token {
        val column = currentParseColumn
        currentParsePosition++
        currentParseColumn++
        return Token(type, currentParseLine, column)
    }

    fun sequenceSymbolToken(type: TokenType, value: String = type.literal): Token {
        val column = currentParseColumn
        currentParsePosition += value.length
        currentParseColumn += value.length
        return Token(type, currentParseLine, column, value)
    }

    fun unknownToken(): Token = Token(
        TokenType.Unrecognized,
        currentParseLine,
        currentParseColumn
    )
}
