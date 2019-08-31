package org.carbon

import org.antlr.v4.runtime.*

fun parseFile(input: String) : Any? {
    val charStream = CharStreams.fromString(input)
    val lexer = CarbonLexer(charStream)
    val tokens = CommonTokenStream(lexer)
    val parser = CarbonParser(tokens)

    var noErrors = true
    lexer.removeErrorListener(ConsoleErrorListener.INSTANCE) // Error messages enabled by default -_-
    parser.removeErrorListener(ConsoleErrorListener.INSTANCE)
    val errorListener = object : BaseErrorListener() {
        override fun syntaxError(recognizer: Recognizer<*, *>?, offendingSymbol: Any?, line: Int, charPositionInLine: Int, msg: String?, e: RecognitionException?) {
            noErrors = false
        }
    }

    lexer.addErrorListener(errorListener)
    parser.addErrorListener(errorListener)

    val result = parser.expressionBody()

    return if (noErrors) result else null
}