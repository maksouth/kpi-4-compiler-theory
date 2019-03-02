package lexer

enum class TokenType(val literal: String) {
    Identifier("identifier"),
    Number("number"),

    Plus("plus"),
    Minus("minus"),
    Times("times"),
    Div("div"),

    GreaterThan("greater than"),
    GreaterThanOrEqual("greater than or equals"),
    LessThan("less than"),
    LessThanOrEqual("less than or equal"),
    Equal("equal"),

    Assign("assign"),

    LeftParenthesis("left parenthesis"),
    RightParenthesis("right parenthesis"),

    EndOfInput("end of input"),

    Other("other")

}