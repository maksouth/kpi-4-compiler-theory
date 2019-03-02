package lexer

import lexer.TokenType.*

enum class TokenType(val literal: String) {
    Identifier("identifier"),
    Number("number"),
    StringValue("string"),

    ListType("List"),
    NumberType("Number"),
    StringType("String"),
    BooleanType("Bool"),
    UnitType("Unit"),

    True("true"),
    False("false"),

    Import("import"),
    Function("fun"),
    Structure("struct"),

    If("if"),
    Else("else"),

    Arrow("->"),

    Assign("="),

    OpenRoundBracket("("),
    CloseRoundBracket(")"),

    OpenSquareBracket("["),
    CloseSquareBracket("]"),

    OpenCurlyBracket("{"),
    CloseCurlyBracket("}"),

    Comma(","),
    Semicolon(":"),
    Dot("."),

    EndOfInput("end of input"),

    Unrecognized("unrecognized");

    override fun toString() = literal
}

fun getKeywordType(keyword: String): TokenType = when(keyword) {
    If.literal -> If
    Else.literal -> Else
    Import.literal -> Import
    TokenType.Function.literal -> Function
    else -> TokenType.Unrecognized
}

fun getStandartType(keyword: String): TokenType = when(keyword) {
    ListType.literal -> ListType
    NumberType.literal -> NumberType
    StringType.literal -> StringType
    BooleanType.literal -> BooleanType
    UnitType.literal -> UnitType
    else -> TokenType.Unrecognized
}

fun getBooleanType(keyword: String): TokenType = when(keyword) {
    True.literal -> True
    False.literal -> False
    else -> TokenType.Unrecognized
}

fun getSpecialSymbolType(char: Char): TokenType = when(char) {
    '.' -> Dot
    ',' -> Comma
    ':' -> Semicolon
    '=' -> Assign
    else -> Unrecognized
}

fun getBracketType(char: Char): TokenType = when(char) {
    '[' -> OpenSquareBracket
    ']' -> CloseSquareBracket
    '(' -> OpenRoundBracket
    ')' -> CloseRoundBracket
    '{' -> OpenCurlyBracket
    '}' -> CloseCurlyBracket
    else -> Unrecognized
}

