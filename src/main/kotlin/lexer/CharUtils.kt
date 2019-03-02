package lexer

fun Char.isWhitespaceOrNewLine() = isWhitespace() or isNewLine()

fun Char.isNewLine() = this == '\n'

fun Char.isUnderscore() = this == '_'

fun Char.isOperator() = this == '='

fun Char.isMinus() = this == '-'

fun Char.isQuote() = this == '"'

fun Char.isBrackets() = when(this) {
    '(', ')' -> true
    else -> false
}